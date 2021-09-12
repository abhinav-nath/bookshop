package com.codecafe.bookshop.book;

import com.codecafe.bookshop.book.model.BookView;
import com.codecafe.bookshop.book.persistence.Book;
import com.codecafe.bookshop.book.persistence.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
    }

    @Test
    void shouldFetchAllBooks() {
        Book book = Book.builder()
                .name("Effective Java")
                .author("Joshua Bloch")
                .publicationYear(2001)
                .price(990.00)
                .isbn("0134685997")
                .booksCount(1)
                .averageRating(4.7)
                .build();
        bookRepository.save(book);

        Book anotherBook = Book.builder()
                .name("Cracking the Coding Interview")
                .author("Gayle Laakmann McDowell")
                .publicationYear(2015)
                .price(575.00)
                .isbn("0984782869")
                .booksCount(1)
                .averageRating(4.6)
                .build();
        bookRepository.save(anotherBook);

        List<BookView> books = bookService.fetchAll();

        assertEquals(2, books.size());
        assertTrue(books.stream().anyMatch(b -> "Effective Java".equals(b.getName())));
        assertTrue(books.stream().anyMatch(b -> "Cracking the Coding Interview".equals(b.getName())));
    }

}