package com.codecafe.bookshop.book;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String author;

    @NonNull
    private Double price;

    @NonNull
    private String isbn;

    @NonNull
    private Integer booksCount;

    private Integer publicationYear;
    private Double averageRating;

    public Book(String name, String author, Double price, String isbn, Integer booksCount) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.isbn = isbn;
        this.booksCount = booksCount;
    }

    public BookView toBookView() {
        return BookView.builder()
                .id(this.id)
                .name(this.name)
                .author(this.author)
                .price(this.price)
                .build();
    }
}