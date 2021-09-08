package com.codecafe.bookshop.book.persistence;

import com.codecafe.bookshop.book.persistence.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findAllByOrderByName();

    Optional<BookEntity> findByIsbn(String isbn);

}