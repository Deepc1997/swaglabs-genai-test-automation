package com.deep.qa.swaglabs_testing.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AIDataGenerator {

    public static Object[][] generateLoginData() {

        try {
            System.out.println("🔥 CALLING GEMINI API...");

            String apiKey = System.getenv("GEMINI_API_KEY");

            System.out.println("API KEY = " + apiKey);

            if (apiKey == null || apiKey.isEmpty()) {
                throw new RuntimeException("API KEY missing");
            }

            URL url = new URL(
                    "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey
            );

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String prompt =
                    "Generate exactly 3 SwagLabs login test inputs in JSON array format. " +
                    //"1st user should always be ABCDE" +
                    //"Keep users completely random, not from the users provided by SwagLabs website" +
                    "Each object must contain ONLY username and password. " +
                    "Do NOT include expected result. Return ONLY JSON array.";

            String body = "{"
                    + "\"contents\":[{"
                    + "\"parts\":[{"
                    + "\"text\":\"" + prompt + "\""
                    + "}]"
                    + "}]"
                    + "}";

            conn.getOutputStream().write(body.getBytes(StandardCharsets.UTF_8));

            int responseCode = conn.getResponseCode();
            System.out.println("RESPONSE CODE = " + responseCode);

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            responseCode >= 200 && responseCode < 300
                                    ? conn.getInputStream()
                                    : conn.getErrorStream(),
                            StandardCharsets.UTF_8
                    )
            );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            br.close();

            System.out.println("🔥 RAW RESPONSE:");
            System.out.println(response);

            if (responseCode != 200) {
                throw new RuntimeException("API failed: " + response);
            }

            JSONObject json = new JSONObject(response.toString());

            String content = json.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");

            // extract JSON array safely
            int start = content.indexOf("[");
            int end = content.lastIndexOf("]");
            content = content.substring(start, end + 1);

            JSONArray arr = new JSONArray(content);

            Object[][] data = new Object[arr.length()][2];

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);

                data[i][0] = obj.getString("username");
                data[i][1] = obj.getString("password");
            }

            return data;

        } catch (Exception e) {

            System.out.println("🔥 AI FAILED → USING FALLBACK DATA");
            System.out.println("Reason: " + e.getMessage());

            return new Object[][]{
                    {"standard_user", "secret_sauce"},
                    {"locked_out_user", "secret_sauce"},
                    //{"error_user", "secret_sauce"}
            };
        }
    }

    
    public static String callGeminiAPI(String prompt) {
        try {
            String apiKey = System.getenv("GEMINI_API_KEY");

            if (apiKey == null || apiKey.isEmpty()) {
                throw new RuntimeException("API KEY missing");
            }

            URL url = new URL(
                    "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey
            );

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String body = "{"
                    + "\"contents\":[{"
                    + "\"parts\":[{"
                    + "\"text\":\"" + prompt + "\""
                    + "}]"
                    + "}]"
                    + "}";

            conn.getOutputStream().write(body.getBytes(StandardCharsets.UTF_8));

            int responseCode = conn.getResponseCode();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            responseCode >= 200 && responseCode < 300
                                    ? conn.getInputStream()
                                    : conn.getErrorStream(),
                            StandardCharsets.UTF_8
                    )
            );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            br.close();

            if (responseCode != 200) {
                throw new RuntimeException("API failed: " + response);
            }

            JSONObject json = new JSONObject(response.toString());

            return json.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");

        } catch (Exception e) {
            throw new RuntimeException("Gemini API failed: " + e.getMessage());
        }
    }
    

    public static String generateNextTestSuggestions() {
        try {
            String prompt =
                    "You are a QA automation expert. " +
                    "Suggest 3 test scenarios after a successful login in SwagLabs. " +
                    "Keep them short and clear. Example: Add item to cart, Remove item, Checkout flow. " +
                    "Return as plain text, numbered list.";

            String response = callGeminiAPI(prompt);

            System.out.println("🤖 AI SUGGESTIONS:\n" + response);

            return response;

        } catch (Exception e) {
            System.out.println("AI suggestion failed, skipping..." + e.getMessage());
            return "AI suggestion not available.";
        }
    }

}















//package com.deep.qa.swaglabs_testing.utils;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//
//public class AIDataGenerator {
//
//    public static Object[][] generateLoginData() {
//
//        try {
//            System.out.println("🔥 CALLING GEMINI API...");
//
//            String apiKey = System.getenv("GEMINI_API_KEY");
//
//            System.out.println("API KEY = " + apiKey);
//
//            if (apiKey == null || apiKey.isEmpty()) {
//                throw new RuntimeException("API KEY missing");
//            }
//
//            // ✅ CORRECT GEMINI ENDPOINT
//            //URL url = new URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey.trim());
//            URL url = new URL(
//            	    "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" 
//            	    + apiKey.trim()
//            	);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setDoOutput(true);
//
//            String prompt =
//                    "Generate EXACTLY 3 login test cases in STRICT JSON ARRAY format. " +
//                    "Each object must contain: username, password, expected (SUCCESS or FAIL). " +
//                    "Return ONLY JSON. No explanation.";
//
//            String body = "{"
//                    + "\"contents\":[{"
//                    + "\"parts\":[{"
//                    + "\"text\":\"" + prompt + "\""
//                    + "}]"
//                    + "}]"
//                    + "}";
//
//            conn.getOutputStream().write(body.getBytes(StandardCharsets.UTF_8));
//
//            int responseCode = conn.getResponseCode();
//
//            System.out.println("RESPONSE CODE = " + responseCode);
//
//            BufferedReader br;
//
//            if (responseCode >= 200 && responseCode < 300) {
//                br = new BufferedReader(
//                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
//                );
//            } else {
//                br = new BufferedReader(
//                        new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8)
//                );
//            }
//
//            StringBuilder response = new StringBuilder();
//            String line;
//
//            while ((line = br.readLine()) != null) {
//                response.append(line);
//            }
//
//            br.close();
//
//            System.out.println("🔥 RAW RESPONSE:");
//            System.out.println(response);
//
//            if (responseCode != 200) {
//                throw new RuntimeException("API failed: " + response);
//            }
//
//            // ✅ GEMINI RESPONSE PARSING (IMPORTANT FIX)
//            JSONObject json = new JSONObject(response.toString());
//
//            String content = json
//                    .getJSONArray("candidates")
//                    .getJSONObject(0)
//                    .getJSONObject("content")
//                    .getJSONArray("parts")
//                    .getJSONObject(0)
//                    .getString("text");
//
//            content = content.trim();
//
//            // safety cleanup (in case AI adds extra text)
//            if (!content.startsWith("[")) {
//                int start = content.indexOf("[");
//                int end = content.lastIndexOf("]");
//                if (start != -1 && end != -1) {
//                    content = content.substring(start, end + 1);
//                }
//            }
//
//            JSONArray arr = new JSONArray(content);
//
//            Object[][] data = new Object[arr.length()][3];
//
//            for (int i = 0; i < arr.length(); i++) {
//                JSONObject obj = arr.getJSONObject(i);
//
//                data[i][0] = obj.getString("username");
//                data[i][1] = obj.getString("password");
//                data[i][2] = obj.getString("expected");
//            }
//
//            return data;
//
//        } catch (Exception e) {
//
//            System.out.println("🔥 AI API FAILED → USING FALLBACK DATA");
//            System.out.println("Reason: " + e.getMessage());
//
//            return new Object[][]{
//                    {"standard_user", "secret_sauce", "SUCCESS"},
//                    {"locked_out_user", "secret_sauce", "FAIL"},
//                    {"problem_user", "secret_sauce", "SUCCESS"}
//            };
//        }
//    }
//}
//
//
//










//package com.deep.qa.swaglabs_testing.utils;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;

//public class AIDataGenerator {
//
//    public static Object[][] generateLoginData() {
//
//        try {
//            System.out.println("🔥 CALLING OPENAI API...");
//
//            String apiKey = System.getenv("OPENAI_API_KEY");
//            
//            System.out.println("API KEY = " + apiKey);
//
//            if (apiKey == null || apiKey.isEmpty()) {
//                throw new RuntimeException("API KEY missing");
//            }
//
//            //URL url = new URL("https://api.openai.com/v1/chat/completions");
//            //URL url = new URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey);
//           
//            //URL url = new URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-pro:generateContent?key=" + apiKey);
//            URL url = new URL("https://generativelanguage.googleapis.com/v1beta/models?key=" + apiKey);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//            conn.setRequestMethod("POST");
//            //conn.setRequestProperty("Authorization", "Bearer " + apiKey);
//            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setDoOutput(true);
//
//            String prompt =
//                    "Generate EXACTLY 3 login test cases in STRICT JSON array format. " +
//                    "Each object must contain username, password, expected (SUCCESS or FAIL). " +
//                    "Return ONLY JSON.";
//
////            String body = "{"
////                    + "\"model\":\"gpt-3.5-turbo\","
////                    + "\"messages\":[{\"role\":\"user\",\"content\":\"" + prompt + "\"}]"
////                    + "}";
//            
//            String body = "{"
//                    + "\"contents\":[{"
//                    + "\"parts\":[{"
//                    + "\"text\":\"" + prompt + "\""
//                    + "}]"
//                    + "}]"
//                    + "}";
//            
//
//            conn.getOutputStream().write(body.getBytes(StandardCharsets.UTF_8));
//            
//            System.out.println("RESPONSE CODE = " + conn.getResponseCode());
//
//            int responseCode = conn.getResponseCode();
//
//            BufferedReader br;
//
//            if (responseCode >= 200 && responseCode < 300) {
//                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
//            } else {
//                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
//            }
//
//            StringBuilder response = new StringBuilder();
//            String line;
//
//            while ((line = br.readLine()) != null) {
//                response.append(line);
//            }
//
//            br.close();
//            
//            
//					            
//					            System.out.println("🔥 RAW API RESPONSE:");
//					            System.out.println(response.toString());
//            
//            
//            
//            
//
//            if (responseCode != 200) {
//                throw new RuntimeException("API failed: " + response.toString());
//            }
//
//            JSONObject json = new JSONObject(response.toString());
//
//            String content = json.getJSONArray("choices")
//                    .getJSONObject(0)
//                    .getJSONObject("message")
//                    .getString("content");
//
//            // 🔥 CLEAN JSON (remove weird formatting if AI adds text)
//            content = content.trim();
//
//            if (!content.startsWith("[")) {
//                int start = content.indexOf("[");
//                int end = content.lastIndexOf("]");
//                content = content.substring(start, end + 1);
//            }
//
//            JSONArray arr = new JSONArray(content);
//
//            Object[][] data = new Object[arr.length()][3];
//
//            for (int i = 0; i < arr.length(); i++) {
//                JSONObject obj = arr.getJSONObject(i);
//
//                data[i][0] = obj.getString("username");
//                data[i][1] = obj.getString("password");
//                data[i][2] = obj.getString("expected");
//            }
//
//            return data;
//            
//            
//            
//        } catch (Exception e) {
//
//            System.out.println("🔥 OPENAI API CALL FAILED");
//
//            System.out.println("Reason: " + e.getMessage());
//
//            if (e.getMessage() != null && e.getMessage().contains("insufficient_quota")) {
//                System.out.println("❌ ISSUE: OpenAI quota exhausted (billing required or free credits ended)");
//            }
//
//            System.out.println("➡️ Switching to fallback test data so tests can continue");
//
//            e.printStackTrace();
//
//            return new Object[][]{
//                    {"standard_user", "secret_sauce", "SUCCESS"},
//                    {"locked_out_user", "secret_sauce", "FAIL"},
//                    {"problem_user", "secret_sauce", "SUCCESS"}
//            };
//        }
//            
//            
//            
//
////        } catch (Exception e) {
////
////            System.out.println("⚠️ AI FAILED → USING FALLBACK DATA");
////            e.printStackTrace();
////                      
////
////            // 🔥 FALLBACK (GUARANTEED WORKING)
////            return new Object[][]{
////                    {"standard_user", "secret_sauce", "SUCCESS"},
////                    {"locked_out_user", "secret_sauce", "FAIL"},
////                    {"problem_user", "secret_sauce", "SUCCESS"}
////            };
////        }
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////package com.deep.qa.swaglabs_testing.utils;
////
////import org.json.JSONArray;
////import org.json.JSONObject;
////
////import java.io.BufferedReader;
////import java.io.InputStreamReader;
////import java.net.HttpURLConnection;
////import java.net.URL;
////import java.nio.charset.StandardCharsets;
////
////public class AIDataGenerator {
////
////    public static Object[][] generateLoginData() {
////
////    	
////    	System.out.println("🔥 CALLING OPENAI API...");
////    	
////    	
////    	
////        try {
////            String apiKey = System.getenv("OPENAI_API_KEY");
////
////            if (apiKey == null || apiKey.isEmpty()) {
////                throw new RuntimeException("OPENAI_API_KEY not set");
////            }
////
////            URL url = new URL("https://api.openai.com/v1/chat/completions");
////            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
////
////            conn.setRequestMethod("POST");
////            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
////            conn.setRequestProperty("Content-Type", "application/json");
////            conn.setDoOutput(true);
////
////            String prompt =
////                    "Generate EXACTLY 3 login test cases in STRICT JSON array format. " +
////                    "Each object must have: username (string), password (string), expected (SUCCESS or FAIL). " +
////                    "Return ONLY JSON. No explanation.";
////
////            String body = "{"
////                    + "\"model\":\"gpt-4o-mini\","
////                    + "\"messages\":[{\"role\":\"user\",\"content\":\"" + prompt + "\"}]"
////                    + "}";
////
////            conn.getOutputStream().write(body.getBytes(StandardCharsets.UTF_8));
////
////            BufferedReader br = new BufferedReader(
////                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
////            );
////
////            StringBuilder response = new StringBuilder();
////            String line;
////
////            while ((line = br.readLine()) != null) {
////                response.append(line);
////            }
////
////            br.close();
////
////            JSONObject json = new JSONObject(response.toString());
////
////            String content = json.getJSONArray("choices")
////                    .getJSONObject(0)
////                    .getJSONObject("message")
////                    .getString("content");
////
////            JSONArray arr = new JSONArray(content);
////
////            Object[][] data = new Object[arr.length()][3];
////
////            for (int i = 0; i < arr.length(); i++) {
////                JSONObject obj = arr.getJSONObject(i);
////
////                data[i][0] = obj.getString("username");
////                data[i][1] = obj.getString("password");
////                data[i][2] = obj.getString("expected");
////            }
////
////            return data;
////
////        } catch (Exception e) {
////            throw new RuntimeException("AI Data generation failed", e);
////        }
////    }
////}
////
//
//
//
//
//
//
//
//
////package com.deep.qa.swaglabs_testing.utils;
////
////import org.json.JSONArray;
////import org.json.JSONObject;
////
////import java.io.BufferedReader;
////import java.io.File;
////import java.io.InputStreamReader;
////import java.net.HttpURLConnection;
////import java.net.URL;
////import java.nio.charset.StandardCharsets;
////import java.nio.file.Files;
////import java.nio.file.Paths;
////
////public class AIDataGenerator {
////
////    private static final String FILE_PATH =
////            System.getProperty("user.dir") +
////            "/src/test/resources/testdata/loginData.json";
////
////    public static void generateIfNeeded() {
////
////        File file = new File(FILE_PATH);
////
////        if (file.exists() && file.length() > 0) {
////            System.out.println("AI Data already exists. Skipping API call.");
////            return;
////        }
////
////        try {
////            String apiKey = System.getenv("OPENAI_API_KEY");
////
////            if (apiKey == null || apiKey.isEmpty()) {
////                throw new RuntimeException("OPENAI_API_KEY not set in environment");
////            }
////
////            URL url = new URL("https://api.openai.com/v1/chat/completions");
////            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
////
////            conn.setRequestMethod("POST");
////            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
////            conn.setRequestProperty("Content-Type", "application/json");
////            conn.setDoOutput(true);
////
////            String prompt =
////            	    "Generate 4 login test cases in STRICT JSON array format. " +
////            	    "Each object must contain: username (string), password (string), expected (string: SUCCESS or FAIL only). " +
////            	    "Example: [{\"username\":\"abc\",\"password\":\"123\",\"expected\":\"SUCCESS\"}]";
////
////            String body = "{"
////                    + "\"model\":\"gpt-4o-mini\","
////                    + "\"messages\":[{\"role\":\"user\",\"content\":\"" + prompt + "\"}]"
////                    + "}";
////
////            conn.getOutputStream().write(body.getBytes(StandardCharsets.UTF_8));
////
////            BufferedReader br = new BufferedReader(
////                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)
////            );
////
////            StringBuilder response = new StringBuilder();
////            String line;
////
////            while ((line = br.readLine()) != null) {
////                response.append(line);
////            }
////
////            br.close();
////
////            JSONObject json = new JSONObject(response.toString());
////
////            String content = json.getJSONArray("choices")
////                    .getJSONObject(0)
////                    .getJSONObject("message")
////                    .getString("content");
////
////            // 🔥 CLEAN WRITE (NO GUAVA, NO CONFUSION)
////            Files.writeString(
////                    Paths.get(FILE_PATH),
////                    content,
////                    StandardCharsets.UTF_8
////            );
////
////            System.out.println("AI Data saved to file");
////
////        } catch (Exception e) {
////            System.out.println("AI generation failed, using existing data");
////            e.printStackTrace();
////        }
////    }
////}