package com.golovko.adminpanel.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category extends AbstractEntity {

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Product> products = new ArrayList<>();
}
