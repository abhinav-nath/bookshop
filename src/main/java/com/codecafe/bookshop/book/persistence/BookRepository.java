package com.codecafe.bookshop.book.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByOrderByName();

    List<Book> findByNameStartsWithIgnoreCaseOrAuthorStartsWithIgnoreCase(String name, String authorName);

    Optional<Book> findByIsbn(String isbn);

}