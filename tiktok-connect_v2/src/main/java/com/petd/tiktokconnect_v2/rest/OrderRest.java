package com.petd.tiktokconnect_v2.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.petd.tiktokconnect_v2.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderRest {

  OrderService orderService;

  @GetMapping("/{shopId}/shop")
  public Object getOrderByShop(@PathVariable(value = "shopId") String shopId)
      throws JsonProcessingException {
    return orderService.getOrders(shopId);
  }
}
