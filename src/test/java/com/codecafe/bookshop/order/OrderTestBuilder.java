package com.codecafe.bookshop.order;

import com.codecafe.bookshop.order.model.CreateOrderRequest;
import com.codecafe.bookshop.order.model.DeliveryDetails;
import com.codecafe.bookshop.order.model.Item;

import java.util.List;

public class OrderTestBuilder {

    public static CreateOrderRequest buildCreateOrderRequest(List<Item> items) {
        DeliveryDetails deliveryDetails = buildDeliveryDetails("John Doe", 9087634222L, "abc@test.com", "123 Street", "INDIA");
        return CreateOrderRequest.builder()
                .deliveryDetails(deliveryDetails)
                .items(items)
                .build();
    }

    public static CreateOrderRequest buildCreateOrderRequest(DeliveryDetails deliveryDetails, List<Item> items) {
        return CreateOrderRequest.builder()
                .deliveryDetails(deliveryDetails)
                .items(items)
                .build();
    }

    public static DeliveryDetails buildDeliveryDetails(String name, Long mobileNumber, String email, String address, String country) {
        return DeliveryDetails.builder()
                .name(name)
                .mobileNumber(mobileNumber)
                .email(email)
                .address(address)
                .country(country)
                .build();
    }

}