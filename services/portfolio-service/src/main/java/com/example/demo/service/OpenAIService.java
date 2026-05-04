package com.example.demo.service;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final OkHttpClient client = new OkHttpClient();

    public String getAIInsights(String prompt) {
        try {
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", prompt);

            JSONArray messages = new JSONArray();
            messages.put(message);

            JSONObject bodyJson = new JSONObject();
            bodyJson.put("model", "gpt-4o-mini"); // safe model
            bodyJson.put("messages", messages);

            RequestBody body = RequestBody.create(
                    bodyJson.toString(),
                    MediaType.parse("application/json"));

            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/chat/completions")
                    .post(body)
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();

            String responseBody = response.body().string();

            if (!response.isSuccessful()) {
                return "ERROR BODY: " + responseBody;
            }
            JSONObject json = new JSONObject(responseBody);
            JSONArray choices = json.getJSONArray("choices");
            JSONObject msg = choices.getJSONObject(0).getJSONObject("message");

            return msg.getString("content");

        } catch (Exception e) {
            return "AI service error: " + e.getMessage();
        }
    }
}