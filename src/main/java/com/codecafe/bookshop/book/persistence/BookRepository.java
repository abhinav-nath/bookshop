package com.codecafe.bookshop.book.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByOrderByName();

    Optional<Book> findByIsbn(String isbn);

}