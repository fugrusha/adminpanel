package com.golovko.adminpanel.dto.order;

import com.golovko.adminpanel.domain.OrderStatus;
import com.golovko.adminpanel.dto.customer.CustomerReadDTO;
import com.golovko.adminpanel.dto.orderitem.OrderItemReadDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrderReadExtendedDTO {

    private UUID id;

    private String orderNumber;

    private CustomerReadDTO customer;

    private Double totalSum;

    private OrderStatus status;

    private LocalDateTime createdDate;

    private List<OrderItemReadDTO> items;
}
