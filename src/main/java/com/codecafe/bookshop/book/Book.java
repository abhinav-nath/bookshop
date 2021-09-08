package com.codecafe.bookshop.book;

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

    public BookEntity toBookEntity() {
        return BookEntity.builder()
                .name(name)
                .author(author)
                .price(price)
                .isbn(isbn)
                .publicationYear(publicationYear)
                .averageRating(averageRating)
                .booksCount(booksCount)
                .build();
    }

}