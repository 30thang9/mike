package com.nth.mike.controller.api;

import com.nth.mike.entity.other.City;
import com.nth.mike.entity.other.District;
import com.nth.mike.entity.other.Ward;
import com.nth.mike.service.AreaAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value ="/mike/api/area-address")
public class AreaAddressApiController {
    @Autowired
    private AreaAddressService areaAddressService;

    @GetMapping("/city")
    public ResponseEntity<List<City>> getAllCity() {
        return ResponseEntity.ok(areaAddressService.findAllCity());
    }
    @GetMapping("/city-single/{cityId}")
    public ResponseEntity<City> getCity(@PathVariable Long cityId) {
        return ResponseEntity.ok(areaAddressService.findCityById(cityId));
    }
    @GetMapping("/district/{cityId}")
    public ResponseEntity<List<District>> getDistrictByCity(@PathVariable Long cityId) {
        City city = areaAddressService.findCityById(cityId);
        return ResponseEntity.ok(areaAddressService.findDistrictByCity(city));
    }
    @GetMapping("/ward/{districtId}")
    public ResponseEntity<List<Ward>> getWardByDistrict(@PathVariable Long districtId) {
        District district = areaAddressService.findDistrictById(districtId);
        return ResponseEntity.ok(areaAddressService.findWardByDistrict(district));
    }

}
