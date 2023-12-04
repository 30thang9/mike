package com.nth.mike.model.mapper.product;

import com.nth.mike.entity.Color;
import com.nth.mike.entity.Size;
import com.nth.mike.model.request.product.ProductFilterRequest;
import com.nth.mike.service.ColorService;
import com.nth.mike.service.ObjectCategoryService;
import com.nth.mike.service.ProductCategoryService;
import com.nth.mike.service.SizeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductFilterRequestMapper {

    public static ProductFilterRequest requestToProductFilterRequest(
            String requestFilter,
            ColorService colorService,
            SizeService sizeService,
            ObjectCategoryService objectCategoryService,
            ProductCategoryService productCategoryService) {
        ProductFilterRequest filter = null;

        if (isValidRequestFilter(requestFilter)) {
            filter = new ProductFilterRequest();
            requestFilter = requestFilter.replace(" ", "")
                    .replace("(", "")
                    .replace(")", "");

            String[] fStrings = requestFilter.split(";");
            for (String fStr : fStrings) {
                String[] keyValue = fStr.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];

                    switch (key) {
                        case "c" -> setColors(filter, value, colorService);
                        case "s" -> setSizes(filter, value, sizeService);
                        case "oc" -> setObjectCategory(filter, value, objectCategoryService);
                        case "pc" -> setProductCategory(filter, value, productCategoryService);
                        case "mip" -> setMinExportPrice(filter, value);
                        case "map" -> setMaxExportPrice(filter, value);
                        case "sbi" -> setSortById(filter, value);
                        case "sbn" -> setSortByName(filter, value);
                        case "sbp" -> setSortByPrice(filter, value);
                        case "pag" -> setPagination(filter, value);
                    }
                }
            }
        }

        return filter;
    }

    private static boolean isValidRequestFilter(String requestFilter) {
        return requestFilter != null && requestFilter.startsWith("(;") && requestFilter.endsWith(";)");
    }

    private static void setColors(ProductFilterRequest filter, String value, ColorService colorService) {
        if (isValidString(value)) {
            String[] list = value.split("\\.");
            List<Color> colorList = new ArrayList<>();
            for (String s : list) {
                colorList.add(colorService.findById(parseId(s)));
            }
            filter.setColorList(colorList);
        }
    }

    private static void setSizes(ProductFilterRequest filter, String value, SizeService sizeService) {
        if (isValidString(value)) {
            String[] list = value.split("\\.");
            List<Size> sizeList = new ArrayList<>();
            for (String s : list) {
                sizeList.add(sizeService.findById(parseId(s)));
            }
            filter.setSizeList(sizeList);
        }
    }

    private static void setObjectCategory(ProductFilterRequest filter, String value, ObjectCategoryService objectCategoryService) {
        if (isValidId(value)) {
            filter.setObjectCategory(objectCategoryService.findById(parseId(value)));
        }
    }

    private static void setProductCategory(ProductFilterRequest filter, String value, ProductCategoryService productCategoryService) {
        if (isValidId(value)) {
            filter.setProductCategory(productCategoryService.findById(parseId(value)));
        }
    }

    private static void setMinExportPrice(ProductFilterRequest filter, String value) {
        if (isValidDouble(value)) {
            filter.setMinPrice(Double.parseDouble(value));
        }
    }

    private static void setMaxExportPrice(ProductFilterRequest filter, String value) {
        if (isValidDouble(value)) {
            filter.setMaxPrice(Double.parseDouble(value));
        }
    }

    private static void setSortById(ProductFilterRequest filter, String value) {
        if (isValidString(value)) {
            filter.getSort().setSortById(value.toUpperCase());
        }
    }

    private static void setSortByName(ProductFilterRequest filter, String value) {
        if (isValidString(value)) {
            filter.getSort().setSortByName(value.toUpperCase());
        }
    }

    private static void setSortByPrice(ProductFilterRequest filter, String value) {
        if (isValidString(value)) {
            filter.getSort().setSortByPrice(value.toUpperCase());
        }
    }

    private static void setPagination(ProductFilterRequest filter, String value) {
        if (isValidPagination(value)) {
            List<String> list = Arrays.asList(value.split("\\."));
            int limit = parsePositiveInt(list.get(0));
            int page = parsePositiveInt(list.get(1)) > 0 ? parsePositiveInt(list.get(1)) : 1;

            filter.getPagination().setLimit(limit);
            filter.getPagination().setPage(page);
            filter.getPagination().setOffset((page - 1) * limit);
        }
    }

    private static boolean isValidString(String value) {
        return value != null && !value.isEmpty();
    }

    private static boolean isValidId(String value) {
        return isValidString(value) && value.matches("-?\\d+");
    }

    private static boolean isValidDouble(String value) {
        return isValidString(value) && value.matches("-?\\d+(\\.\\d+)?");
    }

    private static boolean isValidPagination(String value) {
        if (isValidString(value)) {
            List<String> list = Arrays.asList(value.split("\\."));
            return list.size() == 2 && isValidPositiveInt(list.get(0)) && isValidPositiveInt(list.get(1));
        }
        return false;
    }

    private static int parsePositiveInt(String value) {
        return Integer.parseInt(value);
    }

    private static long parseId(String value) {
        return Long.parseLong(value);
    }

    private static boolean isValidPositiveInt(String value) {
        return value.matches("\\d+");
    }
}
