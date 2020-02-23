package org.milan.naucnacentrala.service_es;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.elasticsearch.common.geo.GeoPoint;
import org.milan.naucnacentrala.client.GeocodingClient;
import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.model.dto.UserDTO;
import org.milan.naucnacentrala.model_es.UserES;
import org.milan.naucnacentrala.repository_es.UserRepositoryES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class UserServiceES {

    @Autowired
    UserRepositoryES userRepositoryES;

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


    public UserES mapUsertoUserES(User u) {

        UserES uES = new UserES();

        uES.setId(u.getId());
        uES.setIme(u.getFirstname());
        uES.setPrezime(u.getLastname());
        uES.setNaucneOblasti(u.getNaucneOblastiUser().stream().map(no -> no.getNaziv()).collect(Collectors.toList()));
        uES.setRole(!u.getAuthorities().isEmpty() ? u.getAuthorities().get(0).getName() : null);

        // TODO location
        try {
            uES.setLocation(getGeoPointFromCityName(u.getCity()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uES;

    }

    private GeoPoint getGeoPointFromCityName(String city) throws InterruptedException, ApiException, IOException {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();
        GeocodingResult[] results =  GeocodingApi.geocode(context, city).await();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(results[0].geometry.location));
        return new GeoPoint(results[0].geometry.location.lat, results[0].geometry.location.lng);
    }
}
