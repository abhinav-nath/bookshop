package com.codecafe.bookshop.order;

import com.codecafe.bookshop.book.persistence.Book;
import com.codecafe.bookshop.book.persistence.BookRepository;
import com.codecafe.bookshop.error.exception.BookNotFoundException;
import com.codecafe.bookshop.error.exception.BookOutOfStockException;
import com.codecafe.bookshop.order.model.CreateOrderRequest;
import com.codecafe.bookshop.order.model.Item;
import com.codecafe.bookshop.order.persistence.*;
import com.codecafe.bookshop.order.service.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderedItemRepository orderedItemRepository;

    @Autowired
    private DeliveryInfoRepository deliveryInfoRepository;

    @Autowired
    private BookRepository bookRepository;

    private static final String USERNAME = "Test User";

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
        orderedItemRepository.deleteAll();
        deliveryInfoRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    void shouldCreateAnOrderWithTwoItems() {
        Book book1 = buildBook("Dark Matter", "Blake Crouch", "202101303", 200.00, 5);
        Book book2 = buildBook("Effective Java", "Joshua Baloch", "202101304", 500.00, 5);
        Book savedBook1 = bookRepository.save(book1);
        Book savedBook2 = bookRepository.save(book2);
        Item item1 = Item.builder().bookId(book1.getId()).quantity(1).build();
        Item item2 = Item.builder().bookId(book2.getId()).quantity(2).build();
        CreateOrderRequest createOrderRequest = OrderTestBuilder.buildCreateOrderRequest(Arrays.asList(item1, item2));

        Order order = orderService.createOrder(USERNAME, createOrderRequest);
        assertNotNull(order);

        Optional<Order> savedOrder = orderRepository.findById(order.getId());
        verifySavedOrder(savedOrder);

        List<OrderedItem> savedOrderedItems = orderedItemRepository.findByOrderId(savedOrder.get().getId());
        verifySavedOrderItems(savedOrderedItems);

        List<DeliveryInfo> savedDeliveryInfo = deliveryInfoRepository.findByOrderId(savedOrder.get().getId());
        verifySavedDeliveryInfo(savedDeliveryInfo);

        verifyBookQuantities(savedBook1, savedBook2);
    }

    @Test
    void shouldFailToCreateOrderWhenBooksAreOutOfStock() {
        Book book = buildBook("Dark Matter", "Blake Crouch", "202101303", 200.00, 1);
        bookRepository.save(book);
        CreateOrderRequest createOrderRequest = createOrder(book.getId(), 5);

        assertThrows(BookOutOfStockException.class, () -> orderService.createOrder(USERNAME, createOrderRequest));
    }

    @Test
    void shouldFailToCreateOrderWhenBookIsNotInInventory() {
        CreateOrderRequest createOrderRequest = createOrder(1L, 1);

        assertThrows(BookNotFoundException.class, () -> orderService.createOrder(USERNAME, createOrderRequest));
    }

    @Test
    void shouldFetchListOfOrders() {
        Book book = buildBook("Dark Matter", "Blake Crouch", "202101303", 200.00, 1);
        Book savedBook = bookRepository.save(book);
        CreateOrderRequest createOrderRequest = createOrder(savedBook.getId(), 1);
        orderService.createOrder(USERNAME, createOrderRequest);

        assertEquals(1, orderService.fetchOrderList().size());
    }

    private void verifyBookQuantities(Book savedBook1, Book savedBook2) {
        Optional<Book> updatedBook1 = bookRepository.findById(savedBook1.getId());
        assertNotNull(updatedBook1.get());
        assertEquals(4, updatedBook1.get().getBooksCount());

        Optional<Book> updatedBook2 = bookRepository.findById(savedBook2.getId());
        assertNotNull(updatedBook2.get());
        assertEquals(3, updatedBook2.get().getBooksCount());
    }

    private void verifySavedDeliveryInfo(List<DeliveryInfo> savedDeliveryInfo) {
        assertNotNull(savedDeliveryInfo);
    }

    private void verifySavedOrderItems(List<OrderedItem> savedOrderedItems) {
        assertNotNull(savedOrderedItems);
    }

    private CreateOrderRequest createOrder(Long bookId, int quantity) {
        Item item = Item.builder().bookId(bookId).quantity(quantity).build();
        return OrderTestBuilder.buildCreateOrderRequest(Collections.singletonList(item));
    }

    private void verifySavedOrder(Optional<Order> savedOrder) {
        assertNotNull(savedOrder.get());
        assertEquals(1200.00, savedOrder.get().getAmount());
    }

    private Book buildBook(String name, String author, String isbn, Double price, int count) {
        return Book.builder()
                .name(name)
                .isbn(isbn)
                .author(author)
                .price(price)
                .booksCount(count)
                .build();
    }

}