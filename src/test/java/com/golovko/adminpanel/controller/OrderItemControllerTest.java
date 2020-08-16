package com.golovko.adminpanel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.golovko.adminpanel.domain.OrderStatus;
import com.golovko.adminpanel.dto.customer.CustomerReadDTO;
import com.golovko.adminpanel.dto.order.OrderReadExtendedDTO;
import com.golovko.adminpanel.service.OrderItemService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderItemController.class)
public class OrderItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderItemService orderItemService;

    @Test
    public void testAddProductToOrder() throws Exception {
        UUID productId = UUID.randomUUID();
        CustomerReadDTO customerReadDTO = createCustomerReadDTO();
        OrderReadExtendedDTO orderReadDTO = createOrderReadExtendedDTO(customerReadDTO);

        Mockito.when(orderItemService.addProductToOrder(orderReadDTO.getId(), productId))
                .thenReturn(orderReadDTO);

        String resultJson = mockMvc
                .perform(post("/api/v1/order-carts/{id}/products/{productId}",
                        orderReadDTO.getId(), productId))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        OrderReadExtendedDTO actualResult = objectMapper.readValue(resultJson, OrderReadExtendedDTO.class);
        Assertions.assertThat(orderReadDTO).isEqualToComparingFieldByField(actualResult);

        Mockito.verify(orderItemService).addProductToOrder(orderReadDTO.getId(), productId);
    }

    @Test
    public void testRemoveProductFromOrder() throws Exception {
        UUID productId = UUID.randomUUID();
        CustomerReadDTO customerReadDTO = createCustomerReadDTO();
        OrderReadExtendedDTO orderReadDTO = createOrderReadExtendedDTO(customerReadDTO);

        Mockito.when(orderItemService.removeProductFromOrder(orderReadDTO.getId(), productId))
                .thenReturn(orderReadDTO);

        String resultJson = mockMvc
                .perform(delete("/api/v1/order-carts/{id}/products/{productId}",
                        orderReadDTO.getId(), productId))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        OrderReadExtendedDTO actualResult = objectMapper.readValue(resultJson, OrderReadExtendedDTO.class);
        Assertions.assertThat(orderReadDTO).isEqualToComparingFieldByField(actualResult);

        Mockito.verify(orderItemService)
                .removeProductFromOrder(orderReadDTO.getId(), productId);
    }

    private OrderReadExtendedDTO createOrderReadExtendedDTO(CustomerReadDTO customerDTO) {
        OrderReadExtendedDTO dto = new OrderReadExtendedDTO();
        dto.setCreatedDate(LocalDateTime.now());
        dto.setId(UUID.randomUUID());
        dto.setTotalSum(1000.0);
        dto.setStatus(OrderStatus.COMPLETED);
        dto.setCustomer(customerDTO);
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
        dto.setChatId(12333L);
        dto.setPhone("38000788799");
        return dto;
    }
}
