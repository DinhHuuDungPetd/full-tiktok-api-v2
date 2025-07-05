package com.petd.tiktokconnect_v2.Initializer;

import com.petd.tiktokconnect_v2.Repository.AccountRepository;
import com.petd.tiktokconnect_v2.dto.request.UserRequest;
import com.petd.tiktokconnect_v2.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DataInitializer implements CommandLineRunner {

  UserService userService;
  AccountRepository accountRepository;

  @Override
  public void run(String... args) throws Exception {
    initAdmin();
  }

  private void initAdmin(){
    String  adminMail= "admin@petd.com";
    if(accountRepository.existsByEmail(adminMail)){
      log.info("Admin already exist");
      return;
    }
    UserRequest userRequest = UserRequest.builder()
        .email(adminMail)
        .password("Dung1702@")
        .name("Admin")
        .build();
    userService.createAdmin(userRequest);
    UserRequest owner = UserRequest.builder()
        .email("owner@petd.com")
        .password("Dung1702@")
        .name("Owner")
        .teamName("petd")
        .build();

    userService.createOwner(owner);
  }


}
