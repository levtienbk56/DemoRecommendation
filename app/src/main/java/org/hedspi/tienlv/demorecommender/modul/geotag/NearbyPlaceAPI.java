/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hedspi.tienlv.demorecommender.modul.geotag;

import com.google.gson.Gson;

import org.hedspi.tienlv.demorecommender.helper.http.ClientRequest;
import org.hedspi.tienlv.demorecommender.model.LabelList;
import org.hedspi.tienlv.demorecommender.model.place_api.NearBySearchResult;
import org.hedspi.tienlv.demorecommender.model.place_api.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NearbyPlaceAPI {

    private static final String API_KEY = "AIzaSyBe9ziw-DfzyAnXJIGZy8f4iSI2lOdnVOk";

    public static List<String> getLabels(double lat, double lng) throws IOException {
        List<String> labels = new ArrayList<>();
        int radius = 20;
        while(radius < 100){
            List<Result> results = requestNearbyPlace(lat, lng, radius);
            for(Result result:results){
                for(String label : result.getTypes()){
                    if(LabelList.getList().contains(label)
                            && !labels.contains(label)
                            && !label.equals("atm")
                            && !label.equals("establishment")
                            && !label.equals("")){
                        labels.add(label);
                    }
                }
            }
            if(labels.size() > 0){
                break;
            }else{
                radius += 20;
            }
        }

        return labels;
    }
    /**
     * request nearby place base on coordinate and radius
     * @param lat
     * @param lng
     * @param radius
     * @return   list of results (Result object)
     */
    private static List<Result> requestNearbyPlace(double lat, double lng, int radius) throws IOException {
        List<Result> results = new ArrayList<>();
        List<Result> r = new ArrayList<>();
        try {
            String url;
            Gson gson = new Gson();
            ClientRequest client = new ClientRequest();
            url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +  lat  + "," + lng + "&radius=" + radius + "&key=" + API_KEY;
            String json = client.request(url);
            NearBySearchResult obj = gson.fromJson(json, NearBySearchResult.class);
            results = obj.getResults();
            for (Result x : results) {
                if (Result.listTypes.contains(x.getTypes().get(0))) {
                    r.add(x);
                }
            }
        } catch (NullPointerException e) {

        }

        return r;
    }

}
