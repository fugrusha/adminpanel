package com.golovko.adminpanel.controller;

import com.golovko.adminpanel.dto.order.OrderReadDTO;
import com.golovko.adminpanel.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AdminController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public List<OrderReadDTO> getAllOrders() {
        return orderService.getAllOrders();
    }
}
