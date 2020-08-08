package com.golovko.adminpanel.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Order extends AbstractEntity {

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Customer customer;

    private Double totalSum;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDate;

    @OneToMany(fetch = FetchType.EAGER)
    private List<OrderItem> items = new ArrayList<>();
}
