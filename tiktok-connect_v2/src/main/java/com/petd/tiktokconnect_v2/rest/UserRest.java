package com.petd.tiktokconnect_v2.rest;

import com.petd.tiktokconnect_v2.dto.ApiResponse;
import com.petd.tiktokconnect_v2.dto.request.UserRequest;
import com.petd.tiktokconnect_v2.dto.response.UserResponse;
import com.petd.tiktokconnect_v2.service.UserService;
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
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserRest {

  UserService userService;

  @GetMapping("/me")
  public ApiResponse<UserResponse> getMe() {
    return ApiResponse.<UserResponse>builder()
        .result(userService.getUserLogin())
        .build();
  }

  @PostMapping("/admin/create-owner")
  public ApiResponse<UserResponse> createOwner(@RequestBody UserRequest request) {
    return ApiResponse.<UserResponse>builder()
        .result(userService.createOwner(request))
        .build();
  }

}
