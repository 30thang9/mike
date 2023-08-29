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
public class OrderDetailId implements Serializable {
    @Column(name = "orderId")
    private Long orderId;
    @Column(name = "productId")
    private Long productId;
    @Column(name = "colorId")
    private Long colorId;
    @Column(name = "sizeId")
    private Long sizeId;
}
