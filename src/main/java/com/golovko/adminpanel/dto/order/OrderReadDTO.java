package com.golovko.adminpanel.dto.order;

import com.golovko.adminpanel.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderReadDTO {

    private UUID id;

    private UUID customerId;

    private Double totalSum;

    private OrderStatus status;

    private LocalDateTime createdDate;
}
