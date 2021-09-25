package com.codecafe.bookshop.book.service;

import com.codecafe.bookshop.book.model.AddBookRequest;
import com.codecafe.bookshop.book.model.BookView;
import com.codecafe.bookshop.book.persistence.Book;
import com.codecafe.bookshop.book.persistence.BookRepository;
import com.codecafe.bookshop.error.exception.BookNotFoundException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookView> fetchAll(String searchText) {
        List<Book> bookEntities;

        if (StringUtils.isNotEmpty(searchText))
            bookEntities = bookRepository.findByNameStartsWithIgnoreCaseOrAuthorStartsWithIgnoreCase(searchText, searchText);
        else
            bookEntities = bookRepository.findAllByOrderByName();

        if (CollectionUtils.isNotEmpty(bookEntities))
            return bookEntities.stream().map(Book::toBookView).collect(Collectors.toList());

        return new ArrayList<>();
    }

    public Book fetchBookDetails(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(String.format("Book with id [%d] not found", id)));
    }

    public Book addBook(AddBookRequest addBookRequest) {
        Book book = bookRepository.findByIsbn(addBookRequest.getIsbn()).orElse(addBookRequest.toBook());

        if (book.getId() != null)
            book.addToBooksCount(addBookRequest.getBooksCount());

        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        try {
            bookRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new BookNotFoundException(String.format("Book with id [%d] not found", id));
        }
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void reduceCount(Book book, @Positive int quantity) {
        book.reduceCount(quantity);
        saveBook(book);
    }

}