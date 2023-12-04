package com.nth.mike.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.boot.context.properties.bind.Name;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "evaluations")
@Builder
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "evaluationDate")
    private Date evaluationDate;
    @Column(name = "rating")
    private Integer rating;
    @Column(name = "descriptions")
    private String descriptions;
    @ManyToOne(optional = true)
    @JoinColumn(name = "customerId", referencedColumnName = "id")
    private Account customer;
    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private Product product;
}
