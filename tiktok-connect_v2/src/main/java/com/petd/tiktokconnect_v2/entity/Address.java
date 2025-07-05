package com.petd.tiktokconnect_v2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
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
public class Address extends Base{
  String addressDetails;
  String city;
  String country;
  String name;
  String phoneNumber;
  String postalCode;
  String state;

  @OneToOne(mappedBy = "address")
  Orders orders;
}
