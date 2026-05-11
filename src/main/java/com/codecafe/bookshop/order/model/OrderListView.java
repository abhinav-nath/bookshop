package com.codecafe.bookshop.order.model;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderListView {
    public Long id;
    public LocalDate orderDate;
    public DeliveryDetails deliveryDetails;
    public Double amount;
    public List<OrderItemView> orderedItems;
}
