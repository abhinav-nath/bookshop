package com.codecafe.bookshop.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    ResponseEntity<ListBooksResponse> listAllBooks() {
        List<BookView> bookViews = bookService.fetchAll();
        ListBooksResponse listBooksResponse = bookService.createFrom(bookViews);
        return ResponseEntity.ok(listBooksResponse);
    }

    @PostMapping("/admin/books")
    ResponseEntity<AddBooksResponse> addBooks(@RequestBody AddBooksRequest addBooksRequest) {
        List<BookEntity> books = bookService.addBooks(addBooksRequest);
        AddBooksResponse addBooksResponse = bookService.toAddBooksResponse(books);
        return new ResponseEntity<>(addBooksResponse, HttpStatus.CREATED);
    }

}