package org.milan.naucnacentrala.service_es;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.milan.naucnacentrala.client.GeocodingClient;
import org.milan.naucnacentrala.model.Koautor;
import org.milan.naucnacentrala.model.NaucniRad;
import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.model.dto.UserDTO;
import org.milan.naucnacentrala.model_es.NaucniRadES;
import org.milan.naucnacentrala.model_es.UserES;
import org.milan.naucnacentrala.repository.INaucniRadRepository;
import org.milan.naucnacentrala.repository_es.UserRepositoryES;
import org.milan.naucnacentrala.search_es.dto.BooleanQueryDTO;
import org.milan.naucnacentrala.search_es.dto.QueryFormater;
import org.milan.naucnacentrala.search_es.dto.SearchMapper;
import org.milan.naucnacentrala.search_es.dto.SearchResultDTO;
import org.milan.naucnacentrala.service.NaucniRadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceES {

    @Autowired
    UserRepositoryES userRepositoryES;

    @Autowired
    INaucniRadRepository naucniRadRepository;


    @Autowired
    NaucniRadServiceES naucniRadServiceES;

    @Autowired
    NaucniRadService naucniRadService;

    @Autowired
    QueryFormater queryFormater;


    private final String API_KEY = "AIzaSyDpeMkR3oBsOMYkPZkDYTqpN72jdSO53Z4";


    public UserES saveUser(User u) throws Exception {
        if (userRepositoryES.existsById(u.getId())) {
            System.out.println("Already exists, skipping.");
            return null;
        }
        return userRepositoryES.save(mapUsertoUserES(u));
    }

    public UserES getUser(int id) {
        return userRepositoryES.findById(id).get();
    }

    public UserES getUser(User u) {
        return userRepositoryES.findById(u.getId()).get();
    }


    public UserES getUser(Koautor koa) {

        UserES userES = new UserES();

        userES.setIme(koa.getIme());
        userES.setId(-1);

        try {
            userES.setLocation(getGeoPointFromCityName(koa.getGrad() + ", " + koa.getDrzava()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userES;

    }


    public UserES mapUsertoUserES(User u) {

        UserES uES = new UserES();

        uES.setId(u.getId());
        uES.setIme(u.getFirstname() + " " + u.getLastname());
        uES.setNaucneOblasti(u.getNaucneOblastiUser().stream().map(no -> no.getNaziv()).collect(Collectors.toList()));
        uES.setRole(!u.getAuthorities().isEmpty() ? u.getAuthorities().get(0).getName() : null);


        try {
            uES.setLocation(getGeoPointFromCityName(u.getCity() + ", " + u.getCountry()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uES;

    }

    private GeoPoint getGeoPointFromCityName(String address) throws InterruptedException, ApiException,
            IOException {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();
        GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(results[0].geometry.location));
        return new GeoPoint(results[0].geometry.location.lat, results[0].geometry.location.lng);
    }

    public Iterable<UserES> filterRecenzenti(int nrId) throws IOException, ApiException, InterruptedException {

        NaucniRad nr = naucniRadRepository.findById(nrId).get();
        UserES autorES = userRepositoryES.findById(nr.getAutor().getId()).get();

        String tekst = naucniRadServiceES.extractTextFromPDF(naucniRadService.getPDF(nr.getId()));
        List<GeoPoint> geoPoints = new ArrayList<>();

        geoPoints.add(autorES.getLocation());

        for (Koautor koa: nr.getKoautori()) {
            geoPoints.add(getGeoPointFromCityName(koa.getGrad() + ", " + koa.getDrzava()));
        }



        BoolQueryBuilder bqb = queryFormater.getFilterRecenzentQueryBuilder(nr.getNaucnaOblast().getNaziv(), tekst
                , geoPoints);

        Iterable<UserES> userESList = userRepositoryES.search(bqb);

        return userESList;
    }


}

