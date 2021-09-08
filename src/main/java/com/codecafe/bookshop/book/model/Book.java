package com.codecafe.bookshop.book.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Book {

    private Long id;
    private String name;
    private String author;
    private Double price;
    private String isbn;
    private Integer publicationYear;
    private Double averageRating;
    private Integer booksCount;

}