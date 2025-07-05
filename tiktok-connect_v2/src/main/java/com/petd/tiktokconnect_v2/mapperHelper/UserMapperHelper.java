package com.petd.tiktokconnect_v2.mapperHelper;

import com.petd.tiktokconnect_v2.entity.Account;
import com.petd.tiktokconnect_v2.entity.Team;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserMapperHelper {


  @Named("toEmail")
  public String toEmail(Account account) {
    return account.getEmail();
  }

  @Named("toRole")
  public String toRole(Account account) {
    return account.getRole();
  }

  @Named("toActive")
  public boolean toActive(Account account) {
    return account.isActive();
  }

  @Named("toTeam")
  public String toTeam(Team team) {
    if (team == null) {
      return null;
    }
    return team.getName();
  }
}
