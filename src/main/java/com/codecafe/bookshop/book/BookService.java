package com.codecafe.bookshop.book;

import com.codecafe.bookshop.book.model.*;
import com.codecafe.bookshop.book.persistence.BookEntity;
import com.codecafe.bookshop.book.persistence.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<BookView> fetchAll() {
        List<BookEntity> bookEntities = bookRepository.findAllByOrderByName();
        return bookEntities.stream().map(BookEntity::toBookView).collect(Collectors.toList());
    }

    public List<BookEntity> addBooks(AddBooksRequest addBooksRequest) {
        List<BookEntity> bookRecords = new ArrayList<>();
        for (Book book : addBooksRequest.getBooks()) {
            BookEntity bookRecord = bookRepository.findByIsbn(book.getIsbn()).orElse(BookEntity.createFrom(book));

            if (bookRecord.getId() != null)
                bookRecord.addToBooksCount(book.getBooksCount());

            bookRecords.add(bookRecord);
        }
        return bookRepository.saveAll(bookRecords);
    }

    public AddBooksResponse toResponse(List<BookEntity> bookRecords) {

        List<Book> books = new ArrayList<>();

        for (BookEntity bookRecord : bookRecords) {
            Book book = bookRecord.toBook();
            books.add(book);
        }

        return new AddBooksResponse(books);
    }

    public ListBooksResponse createFrom(List<BookView> bookViews) {
        ListBooksResponse listBooksResponse = new ListBooksResponse();
        listBooksResponse.setBooks(bookViews);
        listBooksResponse.setTotalBooks(bookViews != null ? bookViews.size() : 0);
        return listBooksResponse;
    }

}