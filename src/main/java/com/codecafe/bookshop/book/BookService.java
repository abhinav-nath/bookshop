package com.codecafe.bookshop.book;

import com.codecafe.bookshop.book.model.AddBookRequest;
import com.codecafe.bookshop.book.model.AddBookResponse;
import com.codecafe.bookshop.book.model.BookView;
import com.codecafe.bookshop.book.persistence.Book;
import com.codecafe.bookshop.book.persistence.BookRepository;
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
        List<Book> bookEntities = bookRepository.findAllByOrderByName();
        return bookEntities.stream().map(Book::toBookView).collect(Collectors.toList());
    }

    public Book addBook(AddBookRequest addBookRequest) {
        Book book = bookRepository.findByIsbn(addBookRequest.getIsbn()).orElse(addBookRequest.toBook());

        if (book.getId() != null)
            book.addToBooksCount(addBookRequest.getBooksCount());

        return bookRepository.save(book);
    }

    public AddBookResponse toResponse(Book book) {
        return book.toResponse();
    }

}