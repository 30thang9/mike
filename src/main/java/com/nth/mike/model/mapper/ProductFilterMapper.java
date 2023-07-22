package com.nth.mike.model.mapper;

import com.nth.mike.entity.Color;
import com.nth.mike.entity.Size;
import com.nth.mike.model.dto.ProductFilterDTO;
import com.nth.mike.service.ColorService;
import com.nth.mike.service.ObjectCategoryService;
import com.nth.mike.service.ProductCategoryService;
import com.nth.mike.service.SizeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductFilterMapper {
    public static ProductFilterDTO requestToProductFilterDTO(
            String requestFilter,
            ColorService colorService,
            SizeService sizeService,
            ObjectCategoryService objectCategoryService,
            ProductCategoryService productCategoryService
    ) {
        //filter=(;c=2.1;s=2;oc=2;pc=4;mip=450000;map=600000;sbi=asc;sbn=asc;sbp=asc;)
        ProductFilterDTO filter = new ProductFilterDTO();

        if (requestFilter != null && !requestFilter.equals("")) {
            requestFilter = requestFilter.replace(" ", "")
                    .replace("(", "")
                    .replace(")", "");

            String[] fStrings = requestFilter.split(";");
            for (String fStr : fStrings) {
                if (fStr.startsWith("c=")) {
                    if (fStr.length() > 2) {
                        String colorValue = fStr.substring(2);
                        List<String> list = Arrays.stream(colorValue.split("\\.")).toList();
                        List<Color> colorList=new ArrayList<>();
                        for (String s:list){
                            colorList.add(colorService.findById(Long.parseLong(s)));
                        }
                        filter.setColorList(colorList);
                    }
                } else if (fStr.startsWith("s=")) {
                    if (fStr.length() > 2) {
                        String sizeValue = fStr.substring(2);
                        List<String> list = Arrays.stream(sizeValue.split("\\.")).toList();
                        List<Size> sizeList=new ArrayList<>();
                        for (String s:list){
                            sizeList.add(sizeService.findById(Long.parseLong(s)));
                        }
                        filter.setSizeList(sizeList);
                    }
                } else if (fStr.startsWith("oc=")) {
                    if (fStr.length() > 3) {
                        String objectCategoryValue = fStr.substring(3);
                        filter.setObjectCategory(objectCategoryService.findById(Long.parseLong(objectCategoryValue)));
                    }

                } else if (fStr.startsWith("pc=")) {
                    if (fStr.length() > 3) {
                        String productCategoryValue = fStr.substring(3);
                        filter.setProductCategory(productCategoryService.findById(Long.parseLong(productCategoryValue)));
                    }
                } else if (fStr.startsWith("mip=")) {
                    if (fStr.length() > 4) {
                        String minExportPriceValue = fStr.substring(4);
                        filter.setMinPrice(Double.parseDouble(minExportPriceValue));
                    }
                } else if (fStr.startsWith("map=")) {
                    if (fStr.length() > 4) {
                        String maxExportPriceValue = fStr.substring(4);
                        filter.setMaxPrice(Double.parseDouble(maxExportPriceValue));
                    }
                } else if (fStr.startsWith("sbi=")) {
                    if (fStr.length() > 4) {
                        String sortByIdValue = fStr.substring(4).toUpperCase();
                        filter.getSort().setSortById(sortByIdValue);
                    }
                } else if (fStr.startsWith("sbn=")) {
                    if (fStr.length() > 4) {
                        String sortByNameValue = fStr.substring(4).toUpperCase();
                        filter.getSort().setSortByName(sortByNameValue);
                    }
                } else if (fStr.startsWith("sbp=")) {
                    if (fStr.length() > 4) {
                        String sortByPriceValue = fStr.substring(4).toUpperCase();
                        filter.getSort().setSortByPrice(sortByPriceValue);
                    }
                } else if (fStr.startsWith("pag=")){
                    if (fStr.length() > 4) {
                        String pagination = fStr.substring(4).toUpperCase();
                        List<String> list = Arrays.stream(pagination.split("\\.")).toList();
                        if(list.size() == 2){
                            filter.getPagination().setLimit(Integer.parseInt(list.get(0)));
                            filter.getPagination().setPage(Integer.parseInt(list.get(1))<=0?1:Integer.parseInt(list.get(1)));
                            filter.getPagination().setOffset((filter.getPagination().getPage() - 1) * filter.getPagination().getLimit());
//                            System.out.println(filter.getPagination().getOffset());
                        }
                    }
                }
            }
        }

        return filter;
    }

}
