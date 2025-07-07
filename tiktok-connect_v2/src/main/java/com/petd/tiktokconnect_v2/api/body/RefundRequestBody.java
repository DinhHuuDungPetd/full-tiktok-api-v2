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
public class RefundRequestBody {

  private List<String> returnIds;
  private List<String> orderIds;
  private List<String> buyerUserIds;
  private List<String> returnTypes; // REFUND_ONLY, RETURN_AND_REFUND, REPLACEMENT
  private List<String> returnStatus; // RETURN_OR_REFUND_REQUEST_PENDING, etc.
  private String sellerProposedReturnType; // PARTIAL_REFUND
  private Long createTimeGe;
  private Long createTimeLe;
  private Long updateTimeGe;
  private Long updateTimeLe;
  private List<String> arbitrationStatus;
  private String locale; // e.g. "en"

}
