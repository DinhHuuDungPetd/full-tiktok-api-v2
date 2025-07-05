package com.petd.tiktokconnect_v2.helper;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TikTokApiClient {

  private final TikTokSignatureUtil signatureUtil;
  private final OkHttpClient okHttpClient = new OkHttpClient();

//  @Value("${app.tiktok.baseUrl}")
  private final static String baseUrl = "https://open-api.tiktokglobalshop.com";
//  @Value("${app.tiktok.appKey}")
  private final static String appKey = "6fikpg3c9k15h" ;
//  @Value("${app.tiktok.secret}")
  private final static String secret ="559f7ecd7df57f73118f3b499c6c9d0592c84963";

  public String get(String path, String accessToken, Map<String, String> queryParams) {
    return execute("GET", path, accessToken, queryParams, null);
  }

  public String post(String path, String accessToken, Map<String, String> queryParams, String jsonBody) {
    return execute("POST", path, accessToken, queryParams, jsonBody);
  }


  private String execute(
      String method,
      String path,
      String accessToken,
      Map<String, String> queryParams,
      String jsonBody
  ) {
    long timestamp = Instant.now().getEpochSecond();
    Map<String, String> params = new TreeMap<>();
    params.put("app_key", appKey);
    params.put("timestamp", String.valueOf(timestamp));
    if (queryParams != null) params.putAll(queryParams);

    HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl + path).newBuilder();
    params.forEach(urlBuilder::addQueryParameter);

    RequestBody body = null;
    if ("POST".equalsIgnoreCase(method) && jsonBody != null) {
      body = RequestBody.create(jsonBody, MediaType.get("application/json; charset=utf-8"));
    }

    Request unsigned = new Request.Builder()
        .url(urlBuilder.build())
        .method(method, body)
        .addHeader("Content-Type", "application/json")
        .addHeader("x-tts-access-token", accessToken)
        .build();

    String sign = signatureUtil.generateSignature(unsigned, secret);
    HttpUrl signedUrl = unsigned.url().newBuilder().addQueryParameter("sign", sign).build();

    Request signedRequest = unsigned.newBuilder().url(signedUrl).build();

    try (Response resp = okHttpClient.newCall(signedRequest).execute()) {
      String respBody = resp.body().string();
      if (!resp.isSuccessful()) {
        throw new RuntimeException("Failed TikTok API [" + method + " " + path + "]: "
            + resp.code() + " => " + respBody);
      }
      return respBody;
    } catch (IOException e) {
      throw new RuntimeException("Error calling TikTok API: " + method + " " + path, e);
    }
  }
}
