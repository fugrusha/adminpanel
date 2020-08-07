package com.golovko.adminpanel.dto.category;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryReadDTO {

    private UUID id;

    private String name;

    private Integer order;
}
