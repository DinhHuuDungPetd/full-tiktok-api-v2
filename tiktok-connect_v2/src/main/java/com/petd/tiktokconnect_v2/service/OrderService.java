package com.petd.tiktokconnect_v2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petd.tiktokconnect_v2.api.OrderApi;
import com.petd.tiktokconnect_v2.entity.Profile;
import com.petd.tiktokconnect_v2.entity.Shop;
import com.petd.tiktokconnect_v2.exception.AppException;
import com.petd.tiktokconnect_v2.exception.ErrorCode;
import com.petd.tiktokconnect_v2.helper.TikTokApiClient;
import java.util.List;
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
public class OrderService {

  TikTokApiClient apiClient;
  ObjectMapper mapper;

  UserService userService;
  ShopService shopService;


  public Object getOrders(String shopId) throws JsonProcessingException {
    Shop shop = getShopById(shopId);
    OrderApi orderApi = OrderApi.builder()
        .apiClient(apiClient)
        .accessToken(shop.getAccessToken())
        .shopCipher(shop.getCipher())
        .build();

    String json  = orderApi.callApi();
    try {
      Map<String, Object> result = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
      Object data = result.get("data");
      return data;
    } catch (JsonProcessingException e) {
      throw e;
    }
  }



  private List<Shop> getShopsByUserLogin (){
    Profile profile = userService.getProfileLogin();
    return profile.getShopList();
  }
  private Shop getShopById(String shopId){
    Profile profile = userService.getProfileLogin();
    Shop shop = shopService.findByShopId(shopId);
    String role = profile.getAccount().getRole();

    if(role.equals("role/Admin"))
      return shop;
    if(
        role.equals("role/Owner")
        && shop.getOwner() != null
        && Objects.equals(profile.getId(), shop.getOwner().getId())){
        return shop;
    }
    throw new AppException(ErrorCode.FORBIDDEN_ACTION);
  }
}
