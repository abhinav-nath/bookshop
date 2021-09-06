package com.codecafe.bookshop.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookView> fetchAll() {
        List<Book> books = bookRepository.findAllByOrderByName();

        return books.stream().map(Book::toBookView).collect(Collectors.toList());
    }

}
