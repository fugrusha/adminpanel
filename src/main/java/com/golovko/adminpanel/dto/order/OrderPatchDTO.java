package com.golovko.adminpanel.dto.order;

import com.golovko.adminpanel.domain.OrderStatus;
import com.golovko.adminpanel.domain.PaymentType;
import lombok.Data;

@Data
public class OrderPatchDTO {

    private OrderStatus status;

    private PaymentType paymentType;
}
