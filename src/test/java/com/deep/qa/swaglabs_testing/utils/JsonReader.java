package com.deep.qa.swaglabs_testing.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {

    public static Object[][] getLoginData(String filePath) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File(filePath));

            List<Object[]> dataList = new ArrayList<>();

            for (JsonNode node : root) {

                String username = node.get("username").asText();
                String password = node.get("password").asText();
                boolean expected = node.get("expected").asBoolean();

                dataList.add(new Object[]{username, password, expected});
            }

            return dataList.toArray(new Object[0][]);

        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON file: " + filePath, e);
        }
    }
}