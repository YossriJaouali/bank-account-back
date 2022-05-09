package com.bankaccount.domain.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Page<T> {
    private int page;
    private long total;
    private int numberOfPages;
    private int pageSize;
    private List<T> content;
}
