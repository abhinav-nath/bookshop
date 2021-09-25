package com.codecafe.bookshop.order.controller;

import com.codecafe.bookshop.order.model.CreateOrderRequest;
import com.codecafe.bookshop.order.model.CreateOrderResponse;
import com.codecafe.bookshop.order.model.OrderListView;
import com.codecafe.bookshop.order.persistence.Order;
import com.codecafe.bookshop.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    ResponseEntity<CreateOrderResponse> create(Principal principal, @Valid @RequestBody CreateOrderRequest createOrderRequest) {
        Order createdOrder = orderService.createOrder(principal.getName(), createOrderRequest);
        CreateOrderResponse createOrderResponse = createdOrder.toResponse();
        return new ResponseEntity<>(createOrderResponse, HttpStatus.CREATED);
    }

    @GetMapping("/admin/orders")
    ResponseEntity<List<OrderListView>> fetchOrderList() {
        List<Order> orders = orderService.fetchOrderList();
        List<OrderListView> orderListViews = Order.toOrderListView(orders);
        return ResponseEntity.ok(orderListViews);
    }

}