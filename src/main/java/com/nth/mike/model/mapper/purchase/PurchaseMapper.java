package com.nth.mike.model.mapper.purchase;

import com.nth.mike.entity.Purchase;
import com.nth.mike.model.dto.purchase.PurchaseDTO;
import com.nth.mike.model.mapper.user.AccountMapper;

public class PurchaseMapper {
    public static PurchaseDTO topurchaseDTO(Purchase purchase){
        PurchaseDTO purchaseDTO =new PurchaseDTO();
        purchaseDTO.setId(purchase.getId());
        purchaseDTO.setSupplier(AccountMapper.toAccountDTO(purchase.getSupplier()));
        purchaseDTO.setEmployee(AccountMapper.toAccountDTO(purchase.getEmployee()));
        purchaseDTO.setPaymentMethod(purchase.getPaymentMethod());
        purchaseDTO.setPurchaseDate(purchase.getPurchaseDate());
        purchaseDTO.setTotalAmount(purchase.getTotalAmount());
        return purchaseDTO;
    }
}
