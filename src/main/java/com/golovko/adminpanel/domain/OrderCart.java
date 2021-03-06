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
public class OrderCart extends AbstractEntity {

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Customer customer;

    @Column(unique = true, updatable = false, nullable = false)
    private String orderNumber;

    private Double totalSum;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "orderCart")
    private List<OrderItem> items = new ArrayList<>();
}
