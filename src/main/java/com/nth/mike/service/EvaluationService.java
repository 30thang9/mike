package com.nth.mike.service;

import java.util.List;

import com.nth.mike.entity.Account;
import com.nth.mike.entity.Evaluation;
import com.nth.mike.entity.Product;

public interface EvaluationService {
    List<Evaluation> findByProduct(Product product);

    List<Evaluation> findByAccount(Account account);

    Evaluation findById(Long id);

    Evaluation save(Evaluation evaluation);

    Long deleteById(Long id);

    Double avgRatingByProduct(Product product);

    Long countByProduct(Product product);
}
