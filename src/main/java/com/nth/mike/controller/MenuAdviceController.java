package com.nth.mike.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.nth.mike.entity.ObjectCategory;
import com.nth.mike.entity.ProductCategory;
import com.nth.mike.service.ObjectCategoryService;
import com.nth.mike.service.ProductCategoryService;

@ControllerAdvice
public class MenuAdviceController {

    @Autowired
    private ObjectCategoryService objectCategoryService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @ModelAttribute("menuOCs")
    public List<ObjectCategory> getMenuObjectCategories() {
        return objectCategoryService.findAll();
    }

    @ModelAttribute("menuPCs")
    public List<ProductCategory> getMenuProductCategories() {
        return productCategoryService.findAll();
    }
}
