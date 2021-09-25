package com.codecafe.bookshop.order.service;

import com.codecafe.bookshop.book.persistence.Book;
import com.codecafe.bookshop.book.service.BookService;
import com.codecafe.bookshop.error.exception.BookOutOfStockException;
import com.codecafe.bookshop.order.model.CreateOrderRequest;
import com.codecafe.bookshop.order.model.Item;
import com.codecafe.bookshop.order.persistence.Order;
import com.codecafe.bookshop.order.persistence.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private BookService bookService;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Order createOrder(String userName, @Valid CreateOrderRequest createOrderRequest) {
        List<Book> orderedBooks = new ArrayList<>();

        for (Item item : createOrderRequest.getItems()) {
            long bookId = item.getBookId();
            Book book = bookService.fetchBookDetails(bookId);

            checkBookAvailability(item, book);

            orderedBooks.add(book);
            bookService.reduceCount(book, item.getQuantity());
        }

        Order order = Order.createFrom(userName, createOrderRequest, orderedBooks);
        return orderRepository.save(order);
    }

    private void checkBookAvailability(Item item, Book book) {
        String message = "";
        if (book.getBooksCount() == 0) {
            message = String.format("book id [%d] with name [%s] is currently out of stock", book.getId(), book.getName());
            throw new BookOutOfStockException(message);
        }

        if (book.getBooksCount() < item.getQuantity()) {
            message = String.format("Only %d units of book with id [%d] and name [%s] are available",
                    book.getBooksCount(), book.getId(), book.getName());
            throw new BookOutOfStockException(message);
        }
    }

    public List<Order> fetchOrderList() {
        return orderRepository.findAll();
    }

}