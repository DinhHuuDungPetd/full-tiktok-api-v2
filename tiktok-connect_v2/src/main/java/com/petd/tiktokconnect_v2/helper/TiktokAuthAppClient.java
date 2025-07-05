package com.petd.tiktokconnect_v2.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class TiktokAuthAppClient {

  private final OkHttpClient okHttpClient = new OkHttpClient();

  private static final String BASE_URL = "https://auth.tiktok-shops.com";
  private static final String APP_KEY = "6gfl9omrkcetf";
  private static final String APP_SECRET = "06628b0f3df3fc400663de3a752cdc807fdefebf";

  public String refreshToken(String refreshToken) throws IOException {
    HttpUrl url = HttpUrl.parse(BASE_URL + "/api/v2/token/refresh")
        .newBuilder()
        .addQueryParameter("app_key", APP_KEY)
        .addQueryParameter("app_secret", APP_SECRET)
        .addQueryParameter("grant_type", "refresh_token")
        .addQueryParameter("refresh_token", refreshToken)
        .build();
    return getResponse(url);
  }

  public String getToken(String code) throws IOException {
    HttpUrl url = HttpUrl.parse(BASE_URL + "/api/v2/token/get")
        .newBuilder()
        .addQueryParameter("app_key", APP_KEY)
        .addQueryParameter("app_secret", APP_SECRET)
        .addQueryParameter("grant_type", "authorized_code")
        .addQueryParameter("auth_code", code)
        .build();
    return getResponse(url);
  }

  private String getResponse(HttpUrl url) throws IOException {
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
