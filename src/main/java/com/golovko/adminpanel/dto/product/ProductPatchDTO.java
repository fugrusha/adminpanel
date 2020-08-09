package com.golovko.adminpanel.dto.product;

import lombok.Data;

@Data
public class ProductPatchDTO {

    private String name;

    private String description;

    private String photoUrl;

    private Double price;

    private Integer orderNumber;
}
