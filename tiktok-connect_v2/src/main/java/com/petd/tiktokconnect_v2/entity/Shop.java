package com.petd.tiktokconnect_v2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Shop extends Base {

  @Column(nullable = false)
  String accessToken;

  @Column(nullable = false)
  Long accessTokenExpiry;

  @Column(nullable = false)
  String cipher;

  @Column(nullable = false)
  String shopName;

  String note;

  @Column(nullable = false)
  String refreshToken;

  String country;

  @Column(nullable = false, unique = true)
  String shopId;

  @ManyToOne
  @JoinColumn(name = "owner_id")
  Profile owner;

  @OneToMany(mappedBy = "shop")
  List<Orders> orders = new ArrayList<>();

}