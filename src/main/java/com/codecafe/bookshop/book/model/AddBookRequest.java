package com.codecafe.bookshop.book.model;

import com.codecafe.bookshop.book.persistence.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddBookRequest {

    private String name;
    private String author;
    private Double price;
    private String isbn;
    private Integer publicationYear;
    private Double averageRating;
    private Integer booksCount;

    public Book toBook() {
        return Book.builder()
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