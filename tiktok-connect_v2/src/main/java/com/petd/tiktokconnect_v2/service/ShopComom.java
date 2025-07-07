package com.petd.tiktokconnect_v2.service;

import com.petd.tiktokconnect_v2.entity.Profile;
import com.petd.tiktokconnect_v2.entity.Shop;
import com.petd.tiktokconnect_v2.exception.AppException;
import com.petd.tiktokconnect_v2.exception.ErrorCode;
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
public class ShopComom {

  UserService userService;
  ShopService shopService;

  public Shop getShopById(String shopId){
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
