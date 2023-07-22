package com.nth.mike.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDetailId implements Serializable {
    @Column(name = "purchaseId")
    private Long purchaseId;

    @Column(name = "productId")
    private Long productId;

    @Column(name = "colorId")
    private Long colorId;

    @Column(name = "sizeId")
    private Long sizeId;

    @Column(name = "materialId")
    private Long materialId;

    // Constructors, Equals, and HashCode
}
