package com.codecafe.bookshop.order.model;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderResponse {
    private Long orderId;
    private List<Item> items;
    private DeliveryDetails deliveryDetails;
    private LocalDate orderDate;
    private Double amount;
}