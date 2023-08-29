package com.nth.mike.repository;

import com.nth.mike.entity.other.District;
import com.nth.mike.entity.other.Ward;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepo extends JpaRepository<Ward, Long> {
    @Query("SELECT w FROM Ward w WHERE w.district = :district")
    List<Ward> findByDistrict(District district);
}
