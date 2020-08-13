package com.golovko.adminpanel.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.golovko.adminpanel.dto.customer.CustomerPatchDTO;
import com.golovko.adminpanel.dto.customer.CustomerReadDTO;
import com.golovko.adminpanel.service.CustomerService;
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

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @Test
    public void testGetCustomer() throws Exception {
        CustomerReadDTO readDTO = createCustomerReadDTO();

        Mockito.when(customerService.getCustomer(readDTO.getId())).thenReturn(readDTO);

        String resultJson = mockMvc
                .perform(get("/api/v1/customers/{id}", readDTO.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        CustomerReadDTO actualResult = objectMapper.readValue(resultJson, CustomerReadDTO.class);
        Assertions.assertThat(readDTO).isEqualToComparingFieldByField(actualResult);

        Mockito.verify(customerService).getCustomer(readDTO.getId());
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        CustomerReadDTO c1 = createCustomerReadDTO();
        CustomerReadDTO c2 = createCustomerReadDTO();
        CustomerReadDTO c3 = createCustomerReadDTO();

        List<CustomerReadDTO> expectedResult = List.of(c1, c2, c3);

        Mockito.when(customerService.getAllCustomers()).thenReturn(expectedResult);

        String resultJson = mockMvc
                .perform(get("/api/v1/customers/"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<CustomerReadDTO> actualResult = objectMapper.readValue(resultJson, new TypeReference<>() {});
        Assert.assertEquals(expectedResult, actualResult);

        Mockito.verify(customerService).getAllCustomers();
    }

    @Test
    public void testPatchCustomer() throws Exception {
        CustomerReadDTO readDTO = createCustomerReadDTO();

        CustomerPatchDTO patchDTO = new CustomerPatchDTO();
        patchDTO.setAddress("new address");
        patchDTO.setCity("new city");
        patchDTO.setName("new name");
        patchDTO.setPhone("new Phone");
        patchDTO.setAddress("new address");

        Mockito.when(customerService.patchCustomer(readDTO.getId(), patchDTO)).thenReturn(readDTO);

        String resultJson = mockMvc
                .perform(patch("/api/v1/customers/{id}", readDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        CustomerReadDTO actualResult = objectMapper.readValue(resultJson, CustomerReadDTO.class);
        Assertions.assertThat(readDTO).isEqualToComparingFieldByField(actualResult);

        Mockito.verify(customerService).patchCustomer(readDTO.getId(), patchDTO);
    }

    private CustomerReadDTO createCustomerReadDTO() {
        CustomerReadDTO dto = new CustomerReadDTO();
        dto.setId(UUID.randomUUID());
        dto.setAddress("address");
        dto.setCity("Kiev");
        dto.setName("name");
        dto.setSurname("last name");
        dto.setUsername("username");
        dto.setChatId(123222L);
        dto.setPhone("38000788799");
        return dto;
    }
}
