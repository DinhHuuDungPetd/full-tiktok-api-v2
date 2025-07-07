package com.petd.tiktokconnect_v2.dto.response;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShopResponse {
  String note;
  String shopId;
  String shopName;
  String country;
}
