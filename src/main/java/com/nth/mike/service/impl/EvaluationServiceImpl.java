package com.nth.mike.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.nth.mike.entity.Account;
import com.nth.mike.entity.Evaluation;
import com.nth.mike.entity.Product;
import com.nth.mike.repository.EvaluationRepo;
import com.nth.mike.service.EvaluationService;

@Service
public class EvaluationServiceImpl implements EvaluationService {
    @Autowired
    private EvaluationRepo evaluationRepo;

    @Override
    public List<Evaluation> findByProduct(Product product) {
        return evaluationRepo.findByProduct(product);
    }

    @Override
    public List<Evaluation> findByAccount(Account account) {
        return evaluationRepo.findByAccount(account);
    }

    @Override
    public Evaluation findById(Long id) {
        return evaluationRepo.findById(id).orElse(null);
    }

    @Override
    public Evaluation save(Evaluation evaluation) {
        return evaluationRepo.save(evaluation);
    }

    @Override
    public Long deleteById(Long id) {
        try {
            evaluationRepo.deleteById(id);
            return id;
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Double avgRatingByProduct(Product product) {
        return evaluationRepo.avgRatingByProduct(product);
    }

    @Override
    public Long countByProduct(Product product) {
        return evaluationRepo.countByProduct(product);
    }

}
