package com.codecafe.bookshop.book;

import com.codecafe.bookshop.book.model.AddBookRequest;
import com.codecafe.bookshop.book.model.BookView;
import com.codecafe.bookshop.book.persistence.Book;
import com.codecafe.bookshop.book.persistence.BookRepository;
import com.codecafe.bookshop.error.exception.BookNotFoundException;
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
        bookRepository.deleteById(id);
    }

}