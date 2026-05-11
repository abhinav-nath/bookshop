package com.codecafe.bookshop.order.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.codecafe.bookshop.book.persistence.Book;
import com.codecafe.bookshop.order.model.Item;
import com.codecafe.bookshop.order.model.OrderItemView;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ordered_items")
public class OrderedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @OneToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    private int quantity;

    public OrderedItem(Order order, Book book, int quantity) {
        this.order = order;
        this.book = book;
        this.quantity = quantity;
    }

    public static OrderItemView toOrderedItemView(OrderedItem orderedItem) {
        return OrderItemView.builder()
                            .book(orderedItem.getBook().toBookDetailsView())
                            .quantity(orderedItem.getQuantity())
                            .build();
    }

    public Item toResponse() {
        return Item.builder()
                   .bookId(book.getId())
                   .quantity(quantity)
                   .build();
    }
}