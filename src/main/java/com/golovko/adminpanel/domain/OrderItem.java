package com.golovko.adminpanel.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
public class OrderItem extends AbstractEntity {

    @OneToOne(fetch = FetchType.EAGER)
    private Product product;

    private Integer quantity;

    @ManyToOne
    private Order order;
}
