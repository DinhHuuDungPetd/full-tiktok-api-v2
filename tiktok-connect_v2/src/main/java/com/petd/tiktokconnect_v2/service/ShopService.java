package com.petd.tiktokconnect_v2.service;

import com.petd.tiktokconnect_v2.Mapper.ShopMapper;
import com.petd.tiktokconnect_v2.Repository.ShopRepository;
import com.petd.tiktokconnect_v2.dto.response.ShopResponse;
import com.petd.tiktokconnect_v2.entity.Profile;
import com.petd.tiktokconnect_v2.entity.Shop;
import com.petd.tiktokconnect_v2.exception.AppException;
import com.petd.tiktokconnect_v2.exception.ErrorCode;
import com.petd.tiktokconnect_v2.util.Role;
import java.util.ArrayList;
import java.util.List;
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

  ShopMapper shopMapper;

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
}
