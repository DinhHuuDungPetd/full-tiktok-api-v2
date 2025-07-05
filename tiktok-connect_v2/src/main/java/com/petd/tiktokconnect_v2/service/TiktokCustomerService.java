package com.petd.tiktokconnect_v2.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.petd.tiktokconnect_v2.Repository.ShopRepository;
import com.petd.tiktokconnect_v2.entity.Shop;
import com.petd.tiktokconnect_v2.helper.JsonGetValue;
import com.petd.tiktokconnect_v2.helper.TiktokAuthAppClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TiktokCustomerService {

  ShopRepository shopRepository;
  TiktokAuthAppClient authAppClient;

  private static final long REFRESH_THRESHOLD_SECONDS = 12 * 60 * 60;
  private static final int THREAD_POOL_SIZE = 10;


//  @Scheduled(fixedDelay = 6 * 60 * 60 * 1000) // mỗi 6 tiếng
  public void scheduledRefreshToken() {
    log.info("🔁 Bắt đầu chạy scheduled refresh token...");
    refreshTokenWithThreadPool();
  }

  public void refreshTokenWithThreadPool() {
    List<Shop> shops = shopRepository.findAll();

    ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    for (Shop shop : shops) {
      if (!isExpiringSoon(shop)) continue;

      executor.submit(() -> {
        try {
          String json = authAppClient.refreshToken(shop.getRefreshToken());

          Map<String, Object> result = JsonGetValue.mapObject(json, new TypeReference<>() {});
          Map<String, Object> data = (Map<String, Object>) result.get("data");

          String accessToken = (String) data.get("access_token");
          String refreshToken = (String) data.get("refresh_token");
          long accessTokenExpireIn = Long.parseLong(String.valueOf(data.get("access_token_expire_in")));

          shop.setAccessToken(accessToken);
          shop.setRefreshToken(refreshToken);
          shop.setAccessTokenExpiry(accessTokenExpireIn);

          shopRepository.save(shop);

          log.info("✅ Refreshed token for shop {}", shop.getId());
        } catch (Exception e) {
          log.error("❌ Refresh token failed for shop {}: {}", shop.getId(), e.getMessage());
        }
      });
    }

    executor.shutdown();
  }

  private boolean isExpiringSoon(Shop shop) {
    long now = Instant.now().getEpochSecond();
    return shop.getAccessTokenExpiry() <= (now + REFRESH_THRESHOLD_SECONDS);
  }
}
