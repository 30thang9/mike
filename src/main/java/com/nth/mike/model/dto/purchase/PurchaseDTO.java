package com.nth.mike.model.dto.purchase;

import com.nth.mike.entity.PaymentMethod;
import com.nth.mike.model.dto.user.AccountDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {
    private Long id;
    private AccountDTO supplier;
    private AccountDTO employee;
    private PaymentMethod paymentMethod;
    private Date purchaseDate;
    private Double totalAmount;
}
