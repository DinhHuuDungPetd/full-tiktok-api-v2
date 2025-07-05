package com.petd.tiktokconnect_v2.service;

import com.petd.tiktokconnect_v2.Mapper.UserMapper;
import com.petd.tiktokconnect_v2.Repository.AccountRepository;
import com.petd.tiktokconnect_v2.Repository.ProfileRepository;
import com.petd.tiktokconnect_v2.Repository.TeamRepository;
import com.petd.tiktokconnect_v2.dto.request.UserRequest;
import com.petd.tiktokconnect_v2.dto.response.UserResponse;
import com.petd.tiktokconnect_v2.entity.Account;
import com.petd.tiktokconnect_v2.entity.Profile;
import com.petd.tiktokconnect_v2.entity.Team;
import com.petd.tiktokconnect_v2.exception.AppException;
import com.petd.tiktokconnect_v2.exception.ErrorCode;
import com.petd.tiktokconnect_v2.helper.JwtUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {

  AccountRepository accountRepository;
  ProfileRepository profileRepository;
  TeamRepository teamRepository;
  PasswordService passwordService;
  UserMapper userMapper;
  JwtUtils jwtUtils;


  public UserResponse createAdmin(UserRequest userRequest) {
    return userMapper.userToUserResponse(createUser(userRequest, "role/Admin"));
  }

  public UserResponse createOwner(UserRequest userRequest) {
    if(StringUtils.isBlank(userRequest.getTeamName())) {
      throw new AppException(ErrorCode.TEAM_NAME_IN_VALID);
    }
    return userMapper.userToUserResponse(createUser(userRequest, "role/Owner"));
  }



  public Account getByEmail(String email) {
    return accountRepository.findByEmail(email)
        .orElseThrow(()  -> new AppException(ErrorCode.USER_NOT_FOUND));
  }


  public UserResponse getUserLogin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
      throw new AppException(ErrorCode.USER_NOT_FOUND);
    }
    Account account = (Account) authentication.getPrincipal();
    return userMapper.userToUserResponse(account.getProfile());
  }

  public Profile getProfileLogin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
      throw new AppException(ErrorCode.USER_NOT_FOUND);
    }
    Account account = (Account) authentication.getPrincipal();
    return account.getProfile();
  }

  @Transactional
  protected Profile createUser(UserRequest request, String role) {

     Optional<Account> accountExists = accountRepository.findByEmail(request.getEmail());

     if(accountExists.isPresent()) {
       throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
     }

    Account account = Account.builder()
        .email(request.getEmail())
        .password(passwordService.encodePassword(request.getPassword()))
        .role(role)
        .build();
    accountRepository.save(account);

    Profile profile = Profile.builder()
        .account(account)
        .name(request.getName())
        .build();
    profileRepository.save(profile);

    if(role.equals("role/Owner")) {
      Team team = Team.builder()
          .name(request.getTeamName())
          .owner(profile)
          .build();
      teamRepository.save(team);
      profile.setTeam(team);
      profileRepository.save(profile);
    }

    return profile;
  }
}
