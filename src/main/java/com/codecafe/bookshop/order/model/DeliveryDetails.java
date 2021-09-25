package com.codecafe.bookshop.order.model;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

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