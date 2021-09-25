package com.codecafe.bookshop.order.model;

import com.codecafe.bookshop.book.model.BookDetailsView;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class OrderItemView {

    public int quantity;
    public BookDetailsView book;

}