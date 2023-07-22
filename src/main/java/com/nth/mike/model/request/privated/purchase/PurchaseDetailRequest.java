package com.nth.mike.model.request.privated.purchase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDetailRequest {
    private Long productId;
    private Long colorId;
    private Long sizeId;
    private Long materialId;
    private Integer quantity;
    private Double paymentPrice;
}
