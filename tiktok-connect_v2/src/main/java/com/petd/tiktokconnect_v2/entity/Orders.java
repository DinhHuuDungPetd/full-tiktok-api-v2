package com.petd.tiktokconnect_v2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Orders {
  @Id
  String id;
  String deliveryOptionId;
  String packageId;
  String shippingType;
  String status;
  Float totalAmount;
  String trackingNumber;

  @CreatedDate
  @Column(updatable = false)
  LocalDateTime createdAt;

  @LastModifiedDate
  LocalDateTime updatedAt;


  @ManyToOne
  @JoinColumn(name = "shop_id")
  Shop shop;

  @OneToOne
  @JoinColumn(name = "address_id")
  Address address;

  @OneToMany(mappedBy = "order")
  List<OrderDetails> orderDetails = new ArrayList<>();

}
