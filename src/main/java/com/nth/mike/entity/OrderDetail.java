package com.nth.mike.entity;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "orderDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    @EmbeddedId
    private OrderDetailId id;

    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "id",insertable = false, updatable = false)
    private Order order;

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
    @Column(name = "orderPrice")
    private Double orderPrice;

    // Getters and setters
}

