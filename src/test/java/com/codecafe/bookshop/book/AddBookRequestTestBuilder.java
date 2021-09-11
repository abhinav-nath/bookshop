package com.codecafe.bookshop.book;

import com.codecafe.bookshop.book.model.AddBookRequest;
import com.codecafe.bookshop.book.model.AddBookRequest.AddBookRequestBuilder;

public class AddBookRequestTestBuilder {

    private final AddBookRequestBuilder requestBuilder;

    public AddBookRequestTestBuilder() {
        requestBuilder = AddBookRequest.builder()
                .name("Dark Matter")
                .author("Blake Crouch")
                .publicationYear(2016)
                .price(300.00)
                .isbn("1101904224")
                .booksCount(1)
                .averageRating(4.5);
    }

    public AddBookRequest build() {
        return requestBuilder.build();
    }

    public AddBookRequestTestBuilder withName(String name) {
        requestBuilder.name(name);
        return this;
    }

    public AddBookRequestTestBuilder withAuthor(String author) {
        requestBuilder.author(author);
        return this;
    }

    public AddBookRequestTestBuilder withPublicationYear(Integer publicationYear) {
        requestBuilder.publicationYear(publicationYear);
        return this;
    }

    public AddBookRequestTestBuilder withPrice(Double price) {
        requestBuilder.price(price);
        return this;
    }

    public AddBookRequestTestBuilder withIsbn(String isbn) {
        requestBuilder.isbn(isbn);
        return this;
    }

    public AddBookRequestTestBuilder withBooksCount(Integer booksCount) {
        requestBuilder.booksCount(booksCount);
        return this;
    }

}