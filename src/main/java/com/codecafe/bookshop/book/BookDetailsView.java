package com.codecafe.bookshop.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BookDetailsView {

    private Long id;
    private String name;
    private String author;
    private Double price;
    private String isbn;
    private Integer booksCount;
    private Integer publicationYear;
    private Double averageRating;

}