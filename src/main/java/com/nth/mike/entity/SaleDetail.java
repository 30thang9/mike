package com.nth.mike.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "saleDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDetail {
    @EmbeddedId
    private SaleDetailId id;

    @ManyToOne
    @JoinColumn(name = "saleId", referencedColumnName = "id",insertable = false, updatable = false)
    private Sale sale;

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
