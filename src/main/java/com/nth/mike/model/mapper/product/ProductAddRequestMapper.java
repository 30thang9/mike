package com.nth.mike.model.mapper.product;

import com.nth.mike.entity.BestSellerStatus;
import com.nth.mike.entity.HotStatus;
import com.nth.mike.entity.Product;
import com.nth.mike.entity.ProductStatus;
import com.nth.mike.model.request.product.ProductAddRequest;
import com.nth.mike.service.ObjectCategoryService;
import com.nth.mike.service.ProductCategoryService;
import com.nth.mike.service.SizeImageService;

public class ProductAddRequestMapper {
    public static Product toProduct(ProductAddRequest par, ObjectCategoryService ocs, ProductCategoryService pcs, SizeImageService sis){
        Product product = new Product();
        product.setId(par.getId());
        product.setName(par.getName());
        product.setDescriptions(par.getDescription());
        product.setAvatar(par.getAvatar());
        product.setObjectCategory(ocs.findById(par.getObjectCategoryId()));
        product.setProductCategory(pcs.findById(par.getProductCategoryId()));
        product.setSizeImage(sis.findById(par.getSizeImageId()));
        product.setProductStatus(ProductStatus.valueOf(par.getProductStatus()));
        product.setHotStatus(HotStatus.valueOf(par.getHotStatus()));
        product.setBestSellerStatus(BestSellerStatus.valueOf(par.getBestSellerStatus()));
        return product;
    }
}
