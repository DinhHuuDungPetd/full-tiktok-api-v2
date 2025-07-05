package com.petd.tiktokconnect_v2.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class TiktokAuthAppClient {

  private final OkHttpClient okHttpClient = new OkHttpClient();

  private static final String BASE_URL = "https://auth.tiktok-shops.com";
  private static final String APP_KEY = "6fikpg3c9k15h";
  private static final String APP_SECRET = "559f7ecd7df57f73118f3b499c6c9d0592c84963";

  public String refreshToken(String refreshToken) {
    HttpUrl url = HttpUrl.parse(BASE_URL + "/api/v2/token/refresh")
        .newBuilder()
        .addQueryParameter("app_key", APP_KEY)
        .addQueryParameter("app_secret", APP_SECRET)
        .addQueryParameter("grant_type", "refresh_token")
        .addQueryParameter("refresh_token", refreshToken)
        .build();

    Request request = new Request.Builder()
        .url(url)
        .get()
        .build();

    try (Response response = okHttpClient.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        log.error("Failed to refresh token: HTTP {}", response.code());
        return null;
      }
      return response.body().string();
    } catch (IOException e) {
      log.error("Error refreshing token: {}", e.getMessage(), e);
      return null;
    }
  }
}
