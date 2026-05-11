package com.codecafe.bookshop.order.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Setter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDetails {
    @NotEmpty
    private String name;

    @Email
    @NotEmpty
    private String email;

    @Positive
    private Long mobileNumber;

    @NotEmpty
    private String address;

    @NotEmpty
    private String country;
}