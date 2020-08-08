package com.golovko.adminpanel.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Product extends AbstractEntity {

    private String name;

    private String description;

    private String photoUrl;

    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Category category;

    @Column(unique = true)
    private Integer order;
}
