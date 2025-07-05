package com.petd.tiktokconnect_v2.Mapper;

import com.petd.tiktokconnect_v2.dto.response.ShopResponse;
import com.petd.tiktokconnect_v2.entity.Shop;
import com.petd.tiktokconnect_v2.mapperHelper.UserMapperHelper;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopMapper {

  ShopResponse toShopResponse(Shop shop);

  List<ShopResponse> toShopResponseList(List<Shop> shops);

}
