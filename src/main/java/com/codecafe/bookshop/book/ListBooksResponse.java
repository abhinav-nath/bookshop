package com.codecafe.bookshop.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListBooksResponse {

    private long totalBooks;
    private List<BookView> books;

}
