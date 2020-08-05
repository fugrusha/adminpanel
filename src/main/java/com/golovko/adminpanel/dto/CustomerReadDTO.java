package com.golovko.adminpanel.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CustomerReadDTO {

    private UUID id;

    private String username;

    private String chatId;

    private String name;

    private String surname;

    private String phone;

    private String city;

    private String address;
}
