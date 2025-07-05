package com.petd.tiktokconnect_v2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Profile extends Base {

  @Column(nullable = false)
  String name;

  @ManyToOne
  @JoinColumn(name = "team_id")
  Team team;

  @OneToOne
  @JoinColumn(name = "account_id")
  Account account;

  @OneToOne(mappedBy = "owner")
  Team teamOwner;

  @OneToMany(mappedBy = "owner")
  List<Shop> shopList = new ArrayList<>();

}
