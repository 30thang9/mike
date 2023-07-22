package com.nth.mike.repository;

import com.nth.mike.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseDetailRepo extends JpaRepository<PurchaseDetail, PurchaseDetailId> {
    @Query("SELECT pd FROM PurchaseDetail pd WHERE pd.productDetail.product = :product")
    List<PurchaseDetail> findByProduct(Product product);

    @Query("SELECT pd FROM PurchaseDetail pd WHERE pd.productDetail = :productDetail")
    List<PurchaseDetail> findByProductDetail(ProductDetail productDetail);
    @Query("SELECT pd FROM PurchaseDetail pd WHERE pd.purchase = :purchase")
    List<PurchaseDetail> findByPurchase(Purchase purchase);
}
