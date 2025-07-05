package com.petd.tiktokconnect_v2.service;

import com.petd.tiktokconnect_v2.Repository.ProfileRepository;
import com.petd.tiktokconnect_v2.entity.Profile;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProfileService {

  ProfileRepository profileRepository;


  public Profile getProfileById(String id) {
    return profileRepository.findById(id).orElse(null);
  }

}
