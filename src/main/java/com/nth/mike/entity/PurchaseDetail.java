package com.nth.mike.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "purchaseDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDetail {
    @EmbeddedId
    private PurchaseDetailId id;

    @ManyToOne
    @JoinColumn(name = "purchaseId", referencedColumnName = "id",insertable = false, updatable = false)
    private Purchase purchase;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "productId", referencedColumnName = "productId",insertable = false, updatable = false),
            @JoinColumn(name = "colorId", referencedColumnName = "colorId",insertable = false, updatable = false),
            @JoinColumn(name = "sizeId", referencedColumnName = "sizeId",insertable = false, updatable = false),
            @JoinColumn(name = "materialId", referencedColumnName = "materialId",insertable = false, updatable = false)
    })
    private ProductDetail productDetail;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "paymentPrice")
    private Double paymentPrice;

    // Constructors, Getters, and Setters

}

