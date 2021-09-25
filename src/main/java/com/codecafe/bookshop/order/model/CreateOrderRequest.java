package com.codecafe.bookshop.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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