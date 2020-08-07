package com.golovko.adminpanel.dto.product;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductCreateDTO {

    private String name;

    private String description;

    private String photoUrl;

    private Double price;

    private Integer order;

    private UUID categoryId;
}
