package com.petd.tiktokconnect_v2.api.body;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestBody {
  private String orderStatus;
  private Long createTimeGe;
  private Long createTimeLt;
  private Long updateTimeGe;
  private Long updateTimeLt;
  private String shippingType;
  private String buyerUserId;
  private Boolean isBuyerRequestCancel;
  private List<String> warehouseIds;
}
