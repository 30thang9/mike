package com.nth.mike.repository;

import com.nth.mike.entity.other.City;
import com.nth.mike.entity.other.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepo extends JpaRepository<District, Long> {
    @Query("SELECT d FROM District d WHERE d.city = :city")
    List<District> findByCity(City city);
}
