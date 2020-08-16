package com.golovko.adminpanel.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Customer extends AbstractEntity {

    private String username;

    private Long chatId;

    private String name;

    private String surname;

    private String phone;

    private String city;

    private String address;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @OneToMany(mappedBy = "customer")
    private List<OrderCart> orderCarts = new ArrayList<>();
}
