/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * The Helper class.
 * @author Azeez Boluwatife Abdullahi - 23713593
 */
public class Helper {
    /**
     * Generates a random string based on a given length of characters.
     *
     * @param length the length of characters
     * @return the string
     */
    public String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder randomString = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }
        return randomString.toString();
    }

    /**
     * Reads a file.
     *
     * @param path the path
     * @return the string
     * @throws IOException the io exception
     */
    public String readFile(String path) throws IOException {
        FileReader fr = new FileReader(path);
        int c;
        StringBuilder s = new StringBuilder();
        while((c = fr.read()) != -1){
            s.append((char) c);
        }
        fr.close();
        return s.toString();
    }
    public String convertModelToJSON(Object o){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(o);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "{}";
    }
    public HashMap<String, String> formatStringToMap(String s){
        HashMap<String, String> map = new HashMap<String, String>();
        String[] pairs = s.toString().split("&");
        for (String pair : pairs) {
            String[] kv = pair.split("=");
            String key = kv[0];
            key = URLDecoder.decode(key, StandardCharsets.UTF_8);

            String value;
            try{
                value = kv[1];
            }catch (Exception e){
                value = "";
            }
            value = URLDecoder.decode(value, StandardCharsets.UTF_8);

            map.put(key, value);

        }
        return map;
    }
    public String formatBodyRequest(HttpExchange exchange) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        String line;
        StringBuilder request = new StringBuilder();
        while ((line = in.readLine()) != null) {
            request.append(line);
        }
        return request.toString();
    }
    public HashMap<String, String> formatQuery(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        HashMap<String, String> map = new HashMap<String, String>();
        if(query == null){
            return map;
        }
        return formatStringToMap(query);
    }
}
