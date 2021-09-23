package com.codecafe.bookshop.book.controller;

import com.codecafe.bookshop.book.service.BookService;
import com.codecafe.bookshop.book.model.AddBookRequest;
import com.codecafe.bookshop.book.model.AddBookResponse;
import com.codecafe.bookshop.book.model.BookDetailsView;
import com.codecafe.bookshop.book.model.BookView;
import com.codecafe.bookshop.book.persistence.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    ResponseEntity<List<BookView>> listAllBooks() {
        List<BookView> bookViews = bookService.fetchAll();
        return ResponseEntity.ok(bookViews);
    }

    @GetMapping("/books/{id}")
    ResponseEntity<BookDetailsView> getBookDetails(@PathVariable(required = false) Long id) {
        Book book = bookService.fetchBookDetails(id);
        return ResponseEntity.ok().body(book.toBookDetailsView());
    }

    @PostMapping("/admin/books")
    ResponseEntity<AddBookResponse> addBook(@Valid @RequestBody AddBookRequest addBookRequest) {
        Book book = bookService.addBook(addBookRequest);
        AddBookResponse addBookResponse = book.toAddBookResponse();
        return new ResponseEntity<>(addBookResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/books/{id}")
    ResponseEntity<Void> removeBook(@PathVariable(required = true) Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}