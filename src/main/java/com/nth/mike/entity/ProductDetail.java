package com.nth.mike.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Column(name = "discountPercent", nullable = false, columnDefinition = "double default 0.0")
    private Double discountPercent = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(name = "productDetailStatus", nullable = false, columnDefinition = "ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE'")
    private ProductDetailStatus productDetailStatus = ProductDetailStatus.ACTIVE;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_image_detail",
            joinColumns = {
                    @JoinColumn(name = "color_id"),
                    @JoinColumn(name = "product_id"),
                    @JoinColumn(name = "size_id"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "product_image_id")
            }
    )
    private Set<ProductImage> productImages = new HashSet<>();

}
