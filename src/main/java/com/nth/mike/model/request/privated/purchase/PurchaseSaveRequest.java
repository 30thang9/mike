package com.nth.mike.model.request.privated.purchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseSaveRequest {
    private PurchaseRequest purchase;
    private List<PurchaseDetailRequest> purchaseDetail;
}
