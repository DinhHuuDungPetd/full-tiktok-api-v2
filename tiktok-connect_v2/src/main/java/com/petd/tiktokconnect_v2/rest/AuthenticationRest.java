package com.petd.tiktokconnect_v2.rest;

import com.petd.tiktokconnect_v2.dto.ApiResponse;
import com.petd.tiktokconnect_v2.dto.request.LoginRequest;
import com.petd.tiktokconnect_v2.dto.request.ShopRequest;
import com.petd.tiktokconnect_v2.dto.response.UserResponse;
import com.petd.tiktokconnect_v2.service.AuthenticationService;
import com.petd.tiktokconnect_v2.service.OrderService;
import com.petd.tiktokconnect_v2.service.ShopService;
import com.petd.tiktokconnect_v2.service.TiktokCustomService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationRest {

  AuthenticationService authenticationService;
  TiktokCustomService tiktokCustomerService;
  ShopService shopService;

  @PostMapping("/login")
  public ApiResponse<UserResponse> login(@RequestBody LoginRequest request, HttpServletResponse response)
      throws Exception {
    return ApiResponse.<UserResponse>builder()
        .result(authenticationService.login(request, response))
        .build();

  }
  @GetMapping("/auth/logout")
  public ApiResponse<Void> logout(HttpServletResponse response) {
    log.info("Logout request received");
    authenticationService.logout(response);
    return ApiResponse.<Void>builder()
        .message("Successfully logged out")
        .build();
  }
  @GetMapping("/refresh-token")
  public ApiResponse<Void> refreshToken() {
    tiktokCustomerService.refreshTokenWithThreadPool();
    return ApiResponse.<Void>builder()
        .message("Successfully refreshed token")
        .build();
  }

  @PostMapping("/add/shop")
  public ApiResponse<Void> createShop(@RequestBody ShopRequest shopRequest) throws IOException {
    shopService.addShop(shopRequest);
    return ApiResponse.<Void>builder()
        .message("Shop added")
        .build();
  }

}
