package com.petd.tiktokconnect_v2.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petd.tiktokconnect_v2.helper.TikTokApiClient;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorizedShopApi implements TiktokCallApi {

  TikTokApiClient apiClient;
  String accessToken;



  @Override
  public Map<String, String> createParameters() {
    return Map.of();
  }

  @Override
  public String callApi() throws JsonProcessingException {
    return apiClient.get("/authorization/202309/shops", accessToken,null);
  }
}
