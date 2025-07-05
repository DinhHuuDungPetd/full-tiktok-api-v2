package com.petd.tiktokconnect_v2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.petd.tiktokconnect_v2.Mapper.ShopMapper;
import com.petd.tiktokconnect_v2.Repository.ShopRepository;
import com.petd.tiktokconnect_v2.api.AuthorizedShopApi;
import com.petd.tiktokconnect_v2.dto.request.ShopRequest;
import com.petd.tiktokconnect_v2.dto.response.ShopResponse;
import com.petd.tiktokconnect_v2.entity.Profile;
import com.petd.tiktokconnect_v2.entity.Shop;
import com.petd.tiktokconnect_v2.exception.AppException;
import com.petd.tiktokconnect_v2.exception.ErrorCode;
import com.petd.tiktokconnect_v2.helper.JsonGetValue;
import com.petd.tiktokconnect_v2.helper.TikTokApiClient;
import com.petd.tiktokconnect_v2.helper.TiktokAuthAppClient;
import com.petd.tiktokconnect_v2.util.Role;
import java.io.IOException;
import java.util.ArrayList;
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
public class ShopService {

  ShopRepository shopRepository;
  UserService userService;
  ProfileService profileService;
  TiktokAuthAppClient authAppClient;
  TikTokApiClient tikTokApiClient;

  ShopMapper shopMapper;

  public void addShop(ShopRequest shopRequest) throws IOException {

    Profile profile = profileService.getProfileById(shopRequest.getToken());
    if(profile == null || ! profile.getAccount().getRole().equals("role/Owner")) {
      throw new AppException(ErrorCode.TOKEN_INVALID);
    }
    String response = authAppClient.getToken(shopRequest.getAuthCode());
    log.info(response);

    Map<String, Object> result = JsonGetValue.mapObject(response, new TypeReference<>() {});
    Object codeObj = result.get("code");
    long code = codeObj != null ? Long.parseLong(codeObj.toString()) : -1L;

    if(code != 0) {
      throw new AppException(ErrorCode.CODE_AUTHENTICATION_FAILED);
    }

    Map<String, Object> data = (Map<String, Object>) result.get("data");

    String accessToken = (String) data.get("access_token");
    Object expireObj = data.get("access_token_expire_in");
    Long accessTokenExpiresIn = expireObj instanceof Number ? ((Number) expireObj).longValue() : null;
    String refreshToken = (String) data.get("refresh_token");

    Map<String, String> shopInfo = getInfoShopOnTiktok(accessToken);

    String shopId = shopInfo.get("shopId");

    Shop shop = shopRepository.findByShopId(shopId)
        .orElse(null);
    if(shop == null) {
      shop = new Shop();
      shop.setOwner(profile);
    }
    shop.setShopId(shopInfo.get("shopId"));
    shop.setName(shopRequest.getShopName());
    shop.setNote(shopRequest.getNote());
    shop.setCipher(shopInfo.get("cipher"));
    shop.setCountry(shopInfo.get("region"));
    shop.setAccessTokenExpiry(accessTokenExpiresIn);
    shop.setRefreshToken(refreshToken);
    shop.setAccessToken(accessToken);
    shopRepository.save(shop);
  }

  public Shop findByShopId(String shopId) {
    return shopRepository.findByShopId(shopId)
        .orElseThrow(() -> new AppException(ErrorCode.SHOP_NOT_FOUND));
  }
  public List<ShopResponse> getMyShops() {
    Profile profile = userService.getProfileLogin();
    Role role = Role.fromValue(profile.getAccount().getRole());
    List<Shop> list = new ArrayList<>();

    if(Objects.equals(role, Role.Admin)) {
      list = shopRepository.findAll();
    }
    if(Objects.equals(role, Role.Owner)) {
      list  = shopRepository.findShopByOwner_Id(profile.getId());
    }
    return shopMapper.toShopResponseList(list) ;
  }

  private Map<String, String> getInfoShopOnTiktok(String accessToken)
      throws JsonProcessingException {
    AuthorizedShopApi authorizedShopApi = AuthorizedShopApi.builder()
        .accessToken(accessToken)
        .apiClient(tikTokApiClient)
        .build();
    String response = authorizedShopApi.callApi();
    log.info("response: {}", response);

    Map<String, Object> result = JsonGetValue.mapObject(response, new TypeReference<>() {});
    Map<String, Object> data = (Map<String, Object>) result.get("data");

    List<Map<String, Object>> shops = (List<Map<String, Object>>) data.get("shops");
    if (shops == null || shops.isEmpty()) {
      throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }

    Map<String, Object> infoShopOnTiktok = shops.get(0);

    String shopId = (String) infoShopOnTiktok.get("id");
    String cipher = (String) infoShopOnTiktok.get("cipher");
    String region = (String) infoShopOnTiktok.get("region");

    log.info("Shop ID: {}", shopId);

    return Map.of(
        "shopId", shopId,
        "cipher", cipher,
        "region", region
    );
  }

}
