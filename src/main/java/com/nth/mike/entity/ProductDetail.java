package com.nth.mike.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {

    @EmbeddedId
    private ProductDetailId id;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "colorId", referencedColumnName = "id", insertable = false, updatable = false)
    private Color color;

    @ManyToOne
    @JoinColumn(name = "sizeId", referencedColumnName = "id", insertable = false, updatable = false)
    private Size size;

    @Column(name = "importPrice",nullable = false)
    private Double importPrice;

    @Column(name = "exportPrice",nullable = false)
    private Double exportPrice;

    @Column(name = "quantity",nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer quantity = 0;

}
