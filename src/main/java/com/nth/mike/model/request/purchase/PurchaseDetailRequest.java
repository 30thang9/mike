package com.nth.mike.model.request.purchase;

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
    private Integer quantity;
    private Double paymentPrice;
}
