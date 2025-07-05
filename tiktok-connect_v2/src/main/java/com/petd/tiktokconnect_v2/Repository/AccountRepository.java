package com.petd.tiktokconnect_v2.Repository;

import com.petd.tiktokconnect_v2.entity.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {

  Optional<Account> findByEmail(String email);

  Boolean existsByEmail(String email);

}
