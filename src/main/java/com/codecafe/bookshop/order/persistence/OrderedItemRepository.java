package com.codecafe.bookshop.order.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedItemRepository extends JpaRepository<OrderedItem, Long> {
    List<OrderedItem> findByOrderId(Long orderId);
}