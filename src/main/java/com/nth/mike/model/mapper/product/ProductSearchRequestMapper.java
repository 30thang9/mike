package com.nth.mike.model.mapper.product;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

        if (requestSearch != null && !requestSearch.equals("")
                && requestSearch.startsWith("(;") && requestSearch.endsWith(";)")) {
            search = new ProductSearchRequest();
            requestSearch = requestSearch.replace(" ", "")
                    .replace("(", "")
                    .replace(")", "");

            String[] fStrings = requestSearch.split(";");
            for (String fStr : fStrings) {

                if (fStr.startsWith("sbi=")) {
                    if (fStr.length() > 4) {
                        String sortByIdValue = fStr.substring(4).toUpperCase();
                        search.getSort().setSortById(sortByIdValue);
                    }
                } else if (fStr.startsWith("sbn=")) {
                    if (fStr.length() > 4) {
                        String sortByNameValue = fStr.substring(4).toUpperCase();
                        search.getSort().setSortByName(sortByNameValue);
                    }
                } else if (fStr.startsWith("sbp=")) {
                    if (fStr.length() > 4) {
                        String sortByPriceValue = fStr.substring(4).toUpperCase();
                        search.getSort().setSortByPrice(sortByPriceValue);
                    }
                } else if (fStr.startsWith("pag=")) {
                    if (fStr.length() > 4) {
                        String pagination = fStr.substring(4).toUpperCase();
                        List<String> list = Arrays.stream(pagination.split("\\.")).toList();
                        if (list.size() == 2 && list.get(0).matches("-?\\d+") && list.get(1).matches("-?\\d+")) {
                            search.getPagination().setLimit(Integer.parseInt(list.get(0)));
                            search.getPagination()
                                    .setPage(Integer.parseInt(list.get(1)) <= 0 ? 1 : Integer.parseInt(list.get(1)));
                            search.getPagination().setOffset(
                                    (search.getPagination().getPage() - 1) * search.getPagination().getLimit());
                        }
                    }
                } else if (fStr.startsWith("val=")) {
                    if (fStr.length() > 4) {
                        System.out.println(fStr);
                        String searchValue = fStr.substring(4).trim().replaceAll("\\+", " ");
                        try {
                            searchValue = URLDecoder.decode(searchValue, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        search.setSearch(searchValue);
                    }
                }
            }
        }

        return search;
    }

}
