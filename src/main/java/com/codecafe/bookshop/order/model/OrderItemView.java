package com.codecafe.bookshop.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

import com.codecafe.bookshop.book.model.BookDetailsView;

@Builder
@AllArgsConstructor
public class OrderItemView {
    public int quantity;
    public BookDetailsView book;
}