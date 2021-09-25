package com.codecafe.bookshop.order;

import com.codecafe.bookshop.book.persistence.Book;
import com.codecafe.bookshop.error.exception.BookOutOfStockException;
import com.codecafe.bookshop.order.controller.OrderController;
import com.codecafe.bookshop.order.model.CreateOrderRequest;
import com.codecafe.bookshop.order.model.DeliveryDetails;
import com.codecafe.bookshop.order.model.Item;
import com.codecafe.bookshop.order.persistence.DeliveryInfo;
import com.codecafe.bookshop.order.persistence.Order;
import com.codecafe.bookshop.order.persistence.OrderedItem;
import com.codecafe.bookshop.order.service.OrderService;
import com.codecafe.bookshop.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@WithMockUser
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @MockBean
    UserService userService;

    @Test
    @WithMockUser(authorities = {"USER"})
    void shouldRespond403WhenUserIsNotAdmin() throws Exception {
        mockMvc.perform(get("/admin/orders"))
                .andExpect(status().isForbidden());
        verify(orderService, never()).createOrder(any(), any());
    }

    @Test
    void shouldGive400IfEmptyRequestIsSent() throws Exception {
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CreateOrderRequest.builder().build())))
                .andExpect(status().isBadRequest());
        verify(orderService, never()).createOrder(any(), any());
    }

    @Test
    void shouldGive400IfDeliveryDetailsAreNotPassed() throws Exception {
        List<Item> items = Collections.singletonList(Item.builder().bookId(1L).quantity(5).build());
        CreateOrderRequest createOrderRequest = OrderTestBuilder.buildCreateOrderRequest(null, items);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderRequest)))
                .andExpect(status().isBadRequest());
        verify(orderService, never()).createOrder(any(), any());
    }

    @Test
    void shouldGive400IfItemsAreNotPassed() throws Exception {
        List<Item> items = new ArrayList<>();
        DeliveryDetails deliveryDetails = OrderTestBuilder.buildDeliveryDetails("John Doe", 9087634222L, "abc@test.com", "123 Street", "INDIA");
        CreateOrderRequest createOrderRequest = OrderTestBuilder.buildCreateOrderRequest(deliveryDetails, items);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createOrderRequest)))
                .andExpect(status().isBadRequest());
        verify(orderService, never()).createOrder(any(), any());
    }

    @Test
    void shouldCreateOrderIfValidDetailsArePassed() throws Exception {
        Book book = Book.builder()
                .id(1L)
                .author("JK Rowling")
                .name("Harry Potter")
                .price(300.00)
                .booksCount(1)
                .isbn("100200300")
                .build();
        List<Item> items = Collections.singletonList(Item.builder().bookId(book.getId()).quantity(5).build());

        Order createdOrder = Order.builder()
                .id(1L)
                .amount(100.00)
                .orderedItems(Collections.singletonList(OrderedItem.builder().id(1L).book(book).quantity(1).build()))
                .deliveryInfo(DeliveryInfo.builder().address("123 street").email("abc@xyz.com").country("INDIA").mobileNumber(9981919922L).build())
                .orderDate(LocalDate.now())
                .build();

        when(orderService.createOrder(any(), any())).thenReturn(createdOrder);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(OrderTestBuilder.buildCreateOrderRequest(items))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").value(1));

        verify(orderService, times(1)).createOrder(any(), any());
    }

    @Test
    void shouldGive422IfBooksAreOutOfStock() throws Exception {
        when(orderService.createOrder(any(), any())).thenThrow(BookOutOfStockException.class);
        List<Item> items = Collections.singletonList(Item.builder().bookId(1L).quantity(1).build());

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(OrderTestBuilder.buildCreateOrderRequest(items))))
                .andExpect(status().isUnprocessableEntity());

        verify(orderService, times(1)).createOrder(any(), any());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldGiveDetailsForPlacedOrder() throws Exception {
        Order order = new Order();
        order.setAmount(500D);
        List<Order> orders = new ArrayList<>();
        orders.add(order);

        when(orderService.fetchOrderList()).thenReturn(orders);

        mockMvc.perform(get("/admin/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].amount").value(500));

        verify(orderService, times(1)).fetchOrderList();
    }

}