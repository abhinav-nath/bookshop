package com.codecafe.bookshop.book.persistence;

import com.codecafe.bookshop.book.model.Book;
import com.codecafe.bookshop.book.model.BookView;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public BookEntity(String name, String author, Double price, String isbn, Integer booksCount) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.isbn = isbn;
        this.booksCount = booksCount;
    }

    public static BookEntity createFrom(Book book) {
        return BookEntity.builder()
                .name(book.getName())
                .author(book.getAuthor())
                .price(book.getPrice())
                .isbn(book.getIsbn())
                .publicationYear(book.getPublicationYear())
                .averageRating(book.getAverageRating())
                .booksCount(book.getBooksCount())
                .build();
    }

    public BookView toBookView() {
        return BookView.builder()
                .id(id)
                .name(name)
                .author(author)
                .price(price)
                .build();
    }

    public Book toBook() {
        return Book.builder()
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

    public void addToBooksCount(int booksCount) {
        this.booksCount += booksCount;
    }

}