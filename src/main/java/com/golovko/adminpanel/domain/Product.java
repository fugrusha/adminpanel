package com.golovko.adminpanel.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Product extends AbstractEntity {

    private String name;

    private String description;

    private String photoUrl;

    private Double price;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @Column(unique = true)
    private Integer orderNumber;

//    @ManyToOne
//    private OrderItem orderItem;
}
