package com.petd.tiktokconnect_v2.Mapper;

import com.petd.tiktokconnect_v2.dto.response.UserResponse;
import com.petd.tiktokconnect_v2.entity.Profile;
import com.petd.tiktokconnect_v2.mapperHelper.UserMapperHelper;
import org.apache.catalina.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses= {UserMapperHelper.class})
public interface UserMapper {

  @Mapping(target = "role", source = "account", qualifiedByName = "toRole")
  @Mapping(target = "email", source = "account", qualifiedByName = "toEmail")
  @Mapping(target = "active", source = "account", qualifiedByName = "toActive")
  @Mapping(target = "teamName", source = "team", qualifiedByName = "toTeam")
  UserResponse userToUserResponse(Profile profile);

}
