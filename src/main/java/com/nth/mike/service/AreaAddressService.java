package com.nth.mike.service;

import com.nth.mike.entity.other.City;
import com.nth.mike.entity.other.District;
import com.nth.mike.entity.other.Ward;

import java.util.List;

public interface AreaAddressService {
//    City saveCity(City city);
//    District saveDistrict(District district);
//    Ward saveWard(Ward Ward);
    City findCityById(Long id);
    List<City> findAllCity();
    List<District> findDistrictByCity(City city);

    District findDistrictById(Long id);

    List<Ward> findWardByDistrict(District district);
}
