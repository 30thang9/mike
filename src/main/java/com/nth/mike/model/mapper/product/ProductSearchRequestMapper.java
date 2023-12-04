package com.nth.mike.model.mapper.product;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import com.nth.mike.model.request.product.ProductSearchRequest;
import com.nth.mike.service.ColorService;
import com.nth.mike.service.ObjectCategoryService;
import com.nth.mike.service.ProductCategoryService;
import com.nth.mike.service.SizeService;

public class ProductSearchRequestMapper {
    public static ProductSearchRequest requestToProductSearchRequest(
            String requestSearch,
            ColorService colorService,
            SizeService sizeService,
            ObjectCategoryService objectCategoryService,
            ProductCategoryService productCategoryService) {
        // search=(;val=cb;sbi=asc;sbn=asc;sbp=asc;)
        ProductSearchRequest search = null;

        if (isValidRequestSearch(requestSearch)) {
            search = new ProductSearchRequest();
            requestSearch = requestSearch
                    .replace("(", "")
                    .replace(")", "");

            String[] fStrings = requestSearch.split(";");
            for (String fStr : fStrings) {
                String[] keyValue = fStr.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim();
                    String value = keyValue[1];

                    switch (key) {
                        case "val" -> setSearchValue(search, value);
                        case "sbi" -> setSortById(search, value);
                        case "sbn" -> setSortByName(search, value);
                        case "sbp" -> setSortByPrice(search, value);
                        case "pag" -> setPagination(search, value);
                    }
                }
            }
        }

        return search;
    }

    private static boolean isValidRequestSearch(String requestSearch) {
        return requestSearch != null && !requestSearch.equals("")
                && requestSearch.startsWith("(;") && requestSearch.endsWith(";)");
    }

    private static void setSortById(ProductSearchRequest search, String value) {
        if (isValidString(value)) {
            search.getSort().setSortById(value.toUpperCase());
        }
    }

    private static void setSortByName(ProductSearchRequest search, String value) {
        if (isValidString(value)) {
            search.getSort().setSortByName(value.toUpperCase());
        }
    }

    private static void setSortByPrice(ProductSearchRequest search, String value) {
        if (isValidString(value)) {
            search.getSort().setSortByPrice(value.toUpperCase());
        }
    }

    private static void setPagination(ProductSearchRequest search, String value) {
        if (isValidPagination(value)) {
            List<String> list = Arrays.asList(value.split("\\."));
            int limit = parsePositiveInt(list.get(0));
            int page = parsePositiveInt(list.get(1)) > 0 ? parsePositiveInt(list.get(1)) : 1;

            search.getPagination().setLimit(limit);
            search.getPagination().setPage(page);
            search.getPagination().setOffset((page - 1) * limit);
        }
    }

    private static void setSearchValue(ProductSearchRequest search, String value) {
        if (isValidString(value)) {
            try {
                value = URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
                search.setSearch(value);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isValidString(String value) {
        return value != null && !value.isEmpty();
    }

    private static int parsePositiveInt(String value) {
        return Integer.parseInt(value);
    }

    private static boolean isValidPagination(String value) {
        if (isValidString(value)) {
            List<String> list = Arrays.asList(value.split("\\."));
            return list.size() == 2 && isValidPositiveInt(list.get(0)) && isValidPositiveInt(list.get(1));
        }
        return false;
    }

    private static boolean isValidPositiveInt(String value) {
        return value.matches("\\d+");
    }

}
