/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hedspi.tienlv.demorecommender.helper.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientRequest {

    private final String USER_AGENT = "Mozilla/5.0";

    /**
     * get Json result from Geocoder service
     *
     * @param link
     * @return an String of json result
     */
    public String request(String link) throws IOException {
        String results = "";
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // optional default is GET
            conn.setRequestMethod("GET");

            //add request header
            conn.setRequestProperty("User-Agent", USER_AGENT);

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            StringBuilder out = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                out.append(line);
            }

            results = out.toString();
            conn.disconnect();

        return results;
    }

}
