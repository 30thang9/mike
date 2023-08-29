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
public class SaleDetailId implements Serializable {
    @Column(name = "saleId")
    private Long saleId;

    @Column(name = "productId")
    private Long productId;

    @Column(name = "colorId")
    private Long colorId;

    @Column(name = "sizeId")
    private Long sizeId;
}