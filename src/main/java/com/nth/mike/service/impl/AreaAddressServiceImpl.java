package com.nth.mike.service.impl;

import com.nth.mike.entity.other.City;
import com.nth.mike.entity.other.District;
import com.nth.mike.entity.other.Ward;
import com.nth.mike.repository.CityRepo;
import com.nth.mike.repository.DistrictRepo;
import com.nth.mike.repository.WardRepo;
import com.nth.mike.service.AreaAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AreaAddressServiceImpl implements AreaAddressService {
    @Autowired
    private CityRepo cityRepo;
    @Autowired
    private DistrictRepo districtRepo;
    @Autowired
    private WardRepo wardRepo;
//    @Override
//    public City saveCity(City city) {
//        return ;
//    }
//
//    @Override
//    public District saveDistrict(District district) {
//        return null;
//    }
//
//    @Override
//    public Ward saveWard(Ward Ward) {
//        return null;
//    }

    @Override
    public City findCityById(Long id) {
        return cityRepo.findById(id).orElse(null);
    }

    @Override
    public List<City> findAllCity(){
        return cityRepo.findAll();
    }

    @Override
    public List<District> findAllDistrict() {
        return districtRepo.findAll();
    }

    @Override
    public List<District> findDistrictByCity(City city) {
        return districtRepo.findByCity(city);
    }
    @Override
    public District findDistrictById(Long id) {
        return districtRepo.findById(id).orElse(null);
    }

    @Override
    public List<Ward> findAllWard() {
        return wardRepo.findAll();
    }

    @Override
    public List<Ward> findWardByDistrict(District district) {
        return wardRepo.findByDistrict(district);
    }
}
