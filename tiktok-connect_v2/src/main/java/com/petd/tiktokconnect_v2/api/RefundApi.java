package com.petd.tiktokconnect_v2.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petd.tiktokconnect_v2.api.body.OrderRequestBody;
import com.petd.tiktokconnect_v2.helper.TikTokApiClient;
import java.util.HashMap;
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
public class RefundApi implements TiktokCallApi {

  TikTokApiClient apiClient;

  @Builder.Default
  ObjectMapper mapper = new ObjectMapper();

  OrderRequestBody body;

  @Builder.Default
  String sortField  = "create_time";

  String shopCipher;
  @Builder.Default
  String pageToken ="";

  @Builder.Default
  int pageSize = 10;

  @Builder.Default
  String sortOrder = "DESC";


  String accessToken;


  @Override
  public String callApi() throws JsonProcessingException {
    String bodyJson = mapper.writeValueAsString(body);
    return apiClient.post("/return_refund/202309/returns/search", accessToken , createParameters(), bodyJson);
  }

  public Map<String, String> createParameters () {
    Map<String, String> params = new HashMap<String, String>();
    params.put("shop_cipher", shopCipher);
    params.put("page_size", String.valueOf(pageSize));
    params.put("sort_order", sortOrder);
    params.put("sort_field", sortField);
    params.put("page_token", pageToken);
    return params;
  }

}
