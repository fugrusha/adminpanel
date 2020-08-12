package com.golovko.adminpanel.dto.order;

import com.golovko.adminpanel.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderFilter {

    private OrderStatus status;
}
