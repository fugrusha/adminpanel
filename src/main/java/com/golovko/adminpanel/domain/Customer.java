package com.golovko.adminpanel.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
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

    @OneToMany(mappedBy = "customer")
    private List<OrderCart> orderCarts = new ArrayList<>();
}
