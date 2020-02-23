package org.milan.naucnacentrala.client;

import org.apache.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Service
public class GeocodingClient {

    private final String API_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private final String API_KEY = "AIzaSyDpeMkR3oBsOMYkPZkDYTqpN72jdSO53Z4";

    @Autowired
    RestTemplate restTemplate;

    public Object getCoordinatesFromCityName(String cityName) {
        return restTemplate.getForEntity(API_URL + cityName.replace(" ", "+") + "&key=" + API_KEY, Object.class).getBody();
    }

}
