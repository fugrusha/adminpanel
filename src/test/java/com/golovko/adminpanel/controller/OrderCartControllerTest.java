package com.golovko.adminpanel.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.golovko.adminpanel.domain.OrderStatus;
import com.golovko.adminpanel.dto.customer.CustomerReadDTO;
import com.golovko.adminpanel.dto.order.OrderPatchDTO;
import com.golovko.adminpanel.dto.order.OrderReadDTO;
import com.golovko.adminpanel.dto.order.OrderReadExtendedDTO;
import com.golovko.adminpanel.service.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderCartControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    public void testGetAllCustomerOrders() throws Exception {
        UUID customerId = UUID.randomUUID();
        OrderReadDTO o1 = createOrderReadDTO(customerId);
        OrderReadDTO o2 = createOrderReadDTO(customerId);

        List<OrderReadDTO> expectedResult = List.of(o1, o2);

        Mockito.when(orderService.getAllCustomerOrders(customerId)).thenReturn(expectedResult);

        String resultJson = mockMvc
                .perform(get("/api/v1/customers/{customerId}/order-carts/", customerId))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<OrderReadDTO> actualResult = objectMapper.readValue(resultJson, new TypeReference<>() {});
        Assert.assertEquals(expectedResult, actualResult);

        Mockito.verify(orderService).getAllCustomerOrders(customerId);
    }

    @Test
    public void testGetExtendedOrder() throws Exception {
        CustomerReadDTO customerReadDTO = createCustomerReadDTO();
        OrderReadExtendedDTO orderReadDTO = createOrderReadExtendedDTO(customerReadDTO);

        Mockito.when(orderService.getExtendedOrder(customerReadDTO.getId(), orderReadDTO.getId()))
                .thenReturn(orderReadDTO);

        String resultJson = mockMvc
                .perform(get("/api/v1/customers/{customerId}/order-carts/{id}",
                        customerReadDTO.getId(), orderReadDTO.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        OrderReadExtendedDTO actualResult = objectMapper.readValue(resultJson, OrderReadExtendedDTO.class);
        Assertions.assertThat(orderReadDTO).isEqualToComparingFieldByField(actualResult);

        Mockito.verify(orderService).getExtendedOrder(customerReadDTO.getId(), orderReadDTO.getId());
    }

    @Test
    public void testPatchOrder() throws Exception {
        CustomerReadDTO customerReadDTO = createCustomerReadDTO();
        OrderReadExtendedDTO orderReadDTO = createOrderReadExtendedDTO(customerReadDTO);

        OrderPatchDTO patchDTO = new OrderPatchDTO();
        patchDTO.setStatus(OrderStatus.PROCESSED);

        Mockito.when(orderService.patchOrder(customerReadDTO.getId(), orderReadDTO.getId(), patchDTO))
                .thenReturn(orderReadDTO);

        String resultJson = mockMvc
                .perform(patch("/api/v1/customers/{customerId}/order-carts/{id}",
                        customerReadDTO.getId(), orderReadDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        OrderReadExtendedDTO actualResult = objectMapper.readValue(resultJson, OrderReadExtendedDTO.class);
        Assertions.assertThat(orderReadDTO).isEqualToComparingFieldByField(actualResult);

        Mockito.verify(orderService).patchOrder(customerReadDTO.getId(), orderReadDTO.getId(), patchDTO);
    }

    @Test
    public void testDeleteOrder() throws Exception {
        UUID customerId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/customers/{customerId}/order-carts/{id}",
                customerId, orderId))
                .andExpect(status().isOk());

        Mockito.verify(orderService).deleteOrder(customerId, orderId);
    }

    private OrderReadExtendedDTO createOrderReadExtendedDTO(CustomerReadDTO customerDTO) {
        OrderReadExtendedDTO dto = new OrderReadExtendedDTO();
        dto.setCreatedDate(LocalDateTime.now());
        dto.setId(UUID.randomUUID());
        dto.setOrderNumber("order345");
        dto.setTotalSum(1000.0);
        dto.setStatus(OrderStatus.COMPLETED);
        dto.setCustomer(customerDTO);
        return dto;
    }

    private OrderReadDTO createOrderReadDTO(UUID customerId) {
        OrderReadDTO dto = new OrderReadDTO();
        dto.setCreatedDate(LocalDateTime.now());
        dto.setId(UUID.randomUUID());
        dto.setOrderNumber("order345");
        dto.setTotalSum(1000.0);
        dto.setStatus(OrderStatus.COMPLETED);
        dto.setCustomerId(customerId);
        return dto;
    }

    private CustomerReadDTO createCustomerReadDTO() {
        CustomerReadDTO dto = new CustomerReadDTO();
        dto.setId(UUID.randomUUID());
        dto.setAddress("address");
        dto.setCity("Kiev");
        dto.setName("name");
        dto.setSurname("last name");
        dto.setUsername("username");
        dto.setChatId(1323223L);
        dto.setPhone("38000788799");
        return dto;
    }
}
