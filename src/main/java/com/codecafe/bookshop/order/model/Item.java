package com.codecafe.bookshop.order.model;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Builder
public class Item {

    @NotNull
    @Positive
    private Long bookId;

    @Positive
    private int quantity;

}
