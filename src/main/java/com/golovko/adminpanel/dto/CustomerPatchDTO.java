package com.golovko.adminpanel.dto;

import lombok.Data;

@Data
public class CustomerPatchDTO {

    private String name;

    private String surname;

    private String phone;

    private String city;

    private String address;
}
