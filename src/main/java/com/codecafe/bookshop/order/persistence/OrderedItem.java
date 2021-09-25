package com.codecafe.bookshop.order.persistence;

import com.codecafe.bookshop.book.persistence.Book;
import com.codecafe.bookshop.order.model.Item;
import com.codecafe.bookshop.order.model.OrderItemView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public Item toResponse() {
        return Item.builder()
                .bookId(book.getId())
                .quantity(quantity)
                .build();
    }

    public static OrderItemView toOrderedItemView(OrderedItem orderedItem) {
        return OrderItemView.builder()
                .book(orderedItem.getBook().toBookDetailsView())
                .quantity(orderedItem.getQuantity())
                .build();
    }
}