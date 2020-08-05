package com.golovko.adminpanel.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Setter
@Getter
@Entity
public class Customer extends AbstractEntity {

    private String username;

    private String chatId;

    private String name;

    private String surname;

    private String phone;

    private String city;

    private String address;
}
