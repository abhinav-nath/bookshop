package com.codecafe.bookshop.order.model;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    @Valid
    @NotNull
    @Size(min = 1, message = "At least 1 item is required to place an order")
    private List<Item> items;

    @Valid
    @NotNull
    private DeliveryDetails deliveryDetails;
}