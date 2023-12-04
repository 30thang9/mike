package com.nth.mike.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nth.mike.entity.Account;
import com.nth.mike.entity.Evaluation;
import com.nth.mike.entity.Product;

@Repository
public interface EvaluationRepo extends JpaRepository<Evaluation, Long> {
    @Query("SELECT e FROM Evaluation e WHERE e.product = :product")
    List<Evaluation> findByProduct(Product product);

    @Query("SELECT e FROM Evaluation e WHERE e.customer = :customer")
    List<Evaluation> findByAccount(Account customer);

    @Query("SELECT AVG(e.rating) FROM Evaluation e WHERE e.product = :product")
    Double avgRatingByProduct(Product product);

    @Query("SELECT COUNT(e) FROM Evaluation e WHERE e.product = :product")
    Long countByProduct(Product product);

}