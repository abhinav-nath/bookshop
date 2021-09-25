package com.codecafe.bookshop.book.persistence;

import com.codecafe.bookshop.book.model.BookDetailsView;
import com.codecafe.bookshop.book.model.AddBookResponse;
import com.codecafe.bookshop.book.model.BookView;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotEmpty
    private String name;

    @NonNull
    @NotEmpty
    private String author;

    @NonNull
    @Positive
    private Double price;

    @NonNull
    @NotEmpty
    private String isbn;

    @NonNull
    private Integer booksCount;

    private Integer publicationYear;
    private Double averageRating;

    public Book(@NonNull String name, @NonNull String author, @NonNull Double price, @NonNull String isbn, @NonNull Integer booksCount) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.isbn = isbn;
        this.booksCount = booksCount;
    }

    public void addToBooksCount(int booksCount) {
        this.booksCount += booksCount;
    }

    public BookView toBookView() {
        return BookView.builder()
                .id(id)
                .name(name)
                .author(author)
                .price(price)
                .build();
    }

    public AddBookResponse toAddBookResponse() {
        return AddBookResponse.builder()
                .id(id)
                .name(name)
                .author(author)
                .price(price)
                .isbn(isbn)
                .booksCount(booksCount)
                .publicationYear(publicationYear)
                .averageRating(averageRating)
                .build();
    }

    public BookDetailsView toBookDetailsView() {
        return BookDetailsView.builder()
                .id(id)
                .name(name)
                .author(author)
                .price(price)
                .isbn(isbn)
                .booksCount(booksCount)
                .publicationYear(publicationYear)
                .averageRating(averageRating)
                .build();
    }

    public void reduceCount(int quantity) {
        this.booksCount = this.booksCount - quantity;
    }

}