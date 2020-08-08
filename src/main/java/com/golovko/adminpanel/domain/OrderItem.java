package com.golovko.adminpanel.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Entity
public class OrderItem extends AbstractEntity {

//    @ManyToOne
    private Product product;

    private Integer quantity;

//    @ManyToOne
    private Order order;
}
