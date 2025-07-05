package com.petd.tiktokconnect_v2.Repository;

import com.petd.tiktokconnect_v2.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, String> {

}
