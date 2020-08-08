package com.golovko.adminpanel.dto.orderitem;

import com.golovko.adminpanel.dto.product.ProductReadDTO;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemReadDTO {

    private UUID id;

    private ProductReadDTO product;

    private Integer quantity;
}
