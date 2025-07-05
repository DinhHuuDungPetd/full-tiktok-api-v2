package com.petd.tiktokconnect_v2.rest;


import com.petd.tiktokconnect_v2.dto.ApiResponse;
import com.petd.tiktokconnect_v2.dto.request.ShopRequest;
import com.petd.tiktokconnect_v2.dto.response.ShopResponse;
import com.petd.tiktokconnect_v2.service.ShopService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShopRest {

  ShopService shopService;

  @GetMapping("/my-shops")
  public ApiResponse<List<ShopResponse>> getMyShops() {
    return ApiResponse.<List<ShopResponse>>builder()
        .result(shopService.getMyShops())
        .build();
  }

}
