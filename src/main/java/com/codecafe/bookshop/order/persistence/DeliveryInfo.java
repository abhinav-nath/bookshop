package com.codecafe.bookshop.order.persistence;

import com.codecafe.bookshop.order.model.DeliveryDetails;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "delivery_info")
public class DeliveryInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    private String name;
    private String email;
    private Long mobileNumber;
    private String address;
    private String country;

    public static DeliveryInfo createFrom(Order order, DeliveryDetails deliveryDetails) {
        return DeliveryInfo.builder()
                .order(order)
                .address(deliveryDetails.getAddress())
                .country(deliveryDetails.getCountry())
                .email(deliveryDetails.getEmail())
                .name(deliveryDetails.getName())
                .mobileNumber(deliveryDetails.getMobileNumber())
                .build();
    }

    public DeliveryDetails toResponse() {
        return DeliveryDetails.builder()
                .address(address)
                .country(country)
                .name(name)
                .mobileNumber(mobileNumber)
                .email(email)
                .build();
    }

}