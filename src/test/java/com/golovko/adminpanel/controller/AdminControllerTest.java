package com.golovko.adminpanel.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.golovko.adminpanel.domain.OrderStatus;
import com.golovko.adminpanel.dto.order.OrderFilter;
import com.golovko.adminpanel.dto.order.OrderReadDTO;
import com.golovko.adminpanel.service.OrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    public void testGetAllOrders() throws Exception {
        OrderReadDTO o1 = createOrderReadDTO();
        OrderReadDTO o2 = createOrderReadDTO();
        OrderReadDTO o3 = createOrderReadDTO();

        List<OrderReadDTO> expectedResult = List.of(o1, o2, o3);

        OrderFilter filter = new OrderFilter();

        Mockito.when(orderService.getAllOrders(filter)).thenReturn(expectedResult);

        String resultJson = mockMvc
                .perform(get("/api/v1/order-carts"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<OrderReadDTO> actualResult = objectMapper.readValue(resultJson, new TypeReference<>() {});
        Assert.assertEquals(expectedResult, actualResult);

        Mockito.verify(orderService).getAllOrders(filter);
    }

    @Test
    public void testGetAllOrdersWithFilter() throws Exception {
        OrderReadDTO o1 = createOrderReadDTO();
        OrderReadDTO o2 = createOrderReadDTO();
        OrderReadDTO o3 = createOrderReadDTO();

        List<OrderReadDTO> expectedResult = List.of(o1, o2, o3);

        OrderFilter filter = new OrderFilter();
        filter.setStatus(OrderStatus.PROCESSED);

        Mockito.when(orderService.getAllOrders(filter)).thenReturn(expectedResult);

        String resultJson = mockMvc
                .perform(get("/api/v1/order-carts")
                .param("status", filter.getStatus().toString()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<OrderReadDTO> actualResult = objectMapper.readValue(resultJson, new TypeReference<>() {});
        Assert.assertEquals(expectedResult, actualResult);

        Mockito.verify(orderService).getAllOrders(filter);
    }

    private OrderReadDTO createOrderReadDTO() {
        OrderReadDTO dto = new OrderReadDTO();
        dto.setCreatedDate(LocalDateTime.now());
        dto.setId(UUID.randomUUID());
        dto.setTotalSum(1000.0);
        dto.setStatus(OrderStatus.COMPLETED);
        dto.setCustomerId(UUID.randomUUID());
        return dto;
    }
}
