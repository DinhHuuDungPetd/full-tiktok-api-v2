package com.petd.tiktokconnect_v2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petd.tiktokconnect_v2.api.OrderApi;
import com.petd.tiktokconnect_v2.api.RefundApi;
import com.petd.tiktokconnect_v2.entity.Profile;
import com.petd.tiktokconnect_v2.entity.Shop;
import com.petd.tiktokconnect_v2.exception.AppException;
import com.petd.tiktokconnect_v2.exception.ErrorCode;
import com.petd.tiktokconnect_v2.helper.TikTokApiClient;
import java.util.Map;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RefundService {


  TikTokApiClient apiClient;
  ObjectMapper mapper;
  ShopComom shopComom;


  public Object getRefund(String shopId, String nextPage) throws JsonProcessingException {
    Shop shop = shopComom.getShopById(shopId);
    RefundApi refundApi = RefundApi.builder()
        .apiClient(apiClient)
        .accessToken(shop.getAccessToken())
        .shopCipher(shop.getCipher())
        .pageToken(nextPage)
        .build();

    String json  = refundApi.callApi();
    try {
      Map<String, Object> result = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
      Object data = result.get("data");
      return data;
    } catch (JsonProcessingException e) {
      throw e;
    }
  }



}
