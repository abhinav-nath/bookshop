package com.codecafe.bookshop.order.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

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
