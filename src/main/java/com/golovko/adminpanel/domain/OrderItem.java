package com.golovko.adminpanel.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class OrderItem extends AbstractEntity {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private OrderCart orderCart;

    public OrderItem() {
    }

    public OrderItem(
            OrderCart orderCart,
            Product product,
            Integer quantity
    ) {
        this.orderCart = orderCart;
        this.product = product;
        this.quantity = quantity;
    }
}
