package com.nth.mike.model.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {
    private Integer limit;
    private Integer offset;
    private Integer page;
}
