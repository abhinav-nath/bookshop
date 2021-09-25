package com.codecafe.bookshop.order.persistence;

import com.codecafe.bookshop.book.persistence.Book;
import com.codecafe.bookshop.order.model.CreateOrderRequest;
import com.codecafe.bookshop.order.model.CreateOrderResponse;
import com.codecafe.bookshop.order.model.Item;
import com.codecafe.bookshop.order.model.OrderListView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderedItem> orderedItems;

    private LocalDate orderDate;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "order")
    private DeliveryInfo deliveryInfo;

    private Double amount;

    public Order() {
        orderedItems = new ArrayList<>();
        deliveryInfo = new DeliveryInfo();
    }

    //initialise order
    public static Order createFrom(String userName, CreateOrderRequest createOrderRequest, List<Book> books) {
        Order order = new Order();
        order.setUserName(userName);

        Double orderAmount = 0.0;

        for (Item item : createOrderRequest.getItems()) {
            Optional<Book> book = books.stream().filter(b -> (item.getBookId() == b.getId())).findFirst();
            OrderedItem orderedItem = new OrderedItem(order, book.get(), item.getQuantity());
            order.addOrderedItem(orderedItem);
            orderAmount += (book.get().getPrice() * item.getQuantity());
        }

        DeliveryInfo deliveryInfo = DeliveryInfo.createFrom(order, createOrderRequest.getDeliveryDetails());

        order.setDeliveryInfo(deliveryInfo);
        order.setOrderDate(LocalDate.now());
        order.setAmount(orderAmount);

        return order;
    }

    private void addOrderedItem(OrderedItem orderedItem) {
        orderedItems.add(orderedItem);
    }

    public CreateOrderResponse toResponse() {
        return CreateOrderResponse.builder()
                .orderId(id)
                .deliveryDetails(deliveryInfo.toResponse())
                .items(orderedItems.stream().map(OrderedItem::toResponse).collect(Collectors.toList()))
                .amount(amount)
                .orderDate(orderDate)
                .build();
    }

    public static List<OrderListView> toOrderListView(List<Order> orders) {
        List<OrderListView> orderListViewList = new ArrayList<>();

        for (Order order : orders) {
            OrderListView orderListView = OrderListView.builder()
                    .id(order.getId())
                    .orderDate(order.getOrderDate())
                    .deliveryDetails(order.getDeliveryInfo().toResponse())
                    .amount(order.getAmount())
                    .orderedItems(order.getOrderedItems().stream().map(OrderedItem::toOrderedItemView).collect(Collectors.toList()))
                    .build();
            orderListViewList.add(orderListView);
        }

        return orderListViewList;
    }

}