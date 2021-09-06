package com.codecafe.bookshop.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookView {

    private Long id;
    private String name;
    private String author;
    private Double price;

}
