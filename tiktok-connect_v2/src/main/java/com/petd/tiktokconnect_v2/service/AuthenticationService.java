package com.petd.tiktokconnect_v2.service;


import com.petd.tiktokconnect_v2.Mapper.UserMapper;
import com.petd.tiktokconnect_v2.dto.request.LoginRequest;
import com.petd.tiktokconnect_v2.dto.response.UserResponse;
import com.petd.tiktokconnect_v2.entity.Account;
import com.petd.tiktokconnect_v2.exception.AppException;
import com.petd.tiktokconnect_v2.exception.ErrorCode;
import com.petd.tiktokconnect_v2.helper.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {

  PasswordService passwordService;
  UserMapper userMapper;
  UserService userService;
  JwtUtils jwtUtils;

  public UserResponse login(LoginRequest loginRequest, HttpServletResponse response)
      throws Exception {
    Account account = userService.getByEmail(loginRequest.getEmail());
    if(!passwordService.matches(loginRequest.getPassword(), account.getPassword())) {
      throw new AppException(ErrorCode.USER_NOT_FOUND);
    }
    String token = jwtUtils.generateToken(account.getUsername());

    Cookie cookie = new Cookie("token", token);
    cookie.setHttpOnly(true);
    cookie.setSecure(false);
    cookie.setPath("/");
    cookie.setMaxAge( (int) jwtUtils.getExpiration()/ 1000);

    response.addCookie(cookie);

    return userMapper.userToUserResponse(account.getProfile());
  }

  public void logout(HttpServletResponse response) {
    Cookie cookie = new Cookie("token", "");
    cookie.setHttpOnly(true);
    cookie.setSecure(false);
    cookie.setPath("/");
    cookie.setMaxAge(0);

    response.addCookie(cookie);
  }


}
