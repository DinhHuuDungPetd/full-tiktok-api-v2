package com.petd.tiktokconnect_v2.Repository;

import com.petd.tiktokconnect_v2.entity.Shop;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, String> {

  Optional<Shop> findByShopId(String shopId);

  List<Shop> findShopByOwner_Id(String ownerId);

}
