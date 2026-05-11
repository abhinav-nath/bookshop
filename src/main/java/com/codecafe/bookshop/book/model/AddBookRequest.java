package com.codecafe.bookshop.book.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.codecafe.bookshop.book.persistence.Book;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddBookRequest {
    @NotEmpty
    private String name;

    @NotEmpty
    private String author;

    @Positive
    @NotNull
    private Double price;

    @NotEmpty
    private String isbn;

    @Positive
    private Integer booksCount;

    private Integer publicationYear;
    private Double averageRating;

    public Book toBook() {
        return Book.builder()
                   .name(name)
                   .author(author)
                   .price(price)
                   .isbn(isbn)
                   .booksCount(booksCount)
                   .publicationYear(publicationYear)
                   .averageRating(averageRating)
                   .build();
    }
}