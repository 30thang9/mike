package com.nth.mike.model.request.privated.purchase;

import com.nth.mike.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequest {
    private Long supplierId;
    private Long employeeId;
    private PaymentMethod paymentMethod;
    private Date purchaseDate;
    private Double totalPrice;
}
