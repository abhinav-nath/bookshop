package com.codecafe.bookshop.book;

import com.codecafe.bookshop.book.model.AddBookRequest;
import com.codecafe.bookshop.book.model.AddBookResponse;
import com.codecafe.bookshop.book.model.BookView;
import com.codecafe.bookshop.book.persistence.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/admin/books")
    ResponseEntity<AddBookResponse> addBook(@Valid @RequestBody AddBookRequest addBookRequest) {
        Book book = bookService.addBook(addBookRequest);
        AddBookResponse addBookResponse = book.toAddBookResponse();
        return new ResponseEntity<>(addBookResponse, HttpStatus.CREATED);
    }

}