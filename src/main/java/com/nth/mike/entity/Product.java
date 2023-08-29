package com.nth.mike.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "descriptions", columnDefinition = "TEXT")
    private String descriptions;

    @Column(name = "urlAvatar", nullable = false)
    private String urlAvatar;

    @ManyToOne
    @JoinColumn(name = "oCateId", referencedColumnName = "id")
    private ObjectCategory objectCategory;

    @ManyToOne
    @JoinColumn(name = "pCateId", referencedColumnName = "id")
    private ProductCategory productCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "productStatus", nullable = false, columnDefinition = "ENUM('COMING_SOON', 'AVAILABLE', 'OUT_OF_STOCK', 'DISCONTINUED', 'HIDDEN') DEFAULT 'AVAILABLE'")
    private ProductStatus productStatus = ProductStatus.AVAILABLE;

    @Enumerated(EnumType.STRING)
    @Column(name = "hotStatus", nullable = false, columnDefinition = "ENUM('ACTIVE', 'INACTIVE') DEFAULT 'INACTIVE'")
    private HotStatus hotStatus = HotStatus.INACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(name = "bestSellerStatus", nullable = false, columnDefinition = "ENUM('ACTIVE', 'INACTIVE') DEFAULT 'INACTIVE'")
    private BestSellerStatus bestSellerStatus = BestSellerStatus.INACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(name = "discountStatus", nullable = false, columnDefinition = "ENUM('ACTIVE', 'INACTIVE') DEFAULT 'INACTIVE'")
    private DiscountStatus discountStatus = DiscountStatus.INACTIVE;

    @Column(name = "discountPercent", nullable = false, columnDefinition = "double default 0.0")
    private Double discountPercent = 0.0;

    // constructors, getters and setters
}
