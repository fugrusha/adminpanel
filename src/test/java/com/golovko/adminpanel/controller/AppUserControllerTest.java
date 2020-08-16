package com.golovko.adminpanel.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.golovko.adminpanel.dto.user.AppUserCreateDTO;
import com.golovko.adminpanel.dto.user.AppUserPatchDTO;
import com.golovko.adminpanel.dto.user.AppUserReadDTO;
import com.golovko.adminpanel.service.AppUserService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AppUserController.class)
public class AppUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AppUserService appUserService;

    @Test
    public void testGetUser() throws Exception {
        AppUserReadDTO readDTO = createAppUserReadDTO();

        Mockito.when(appUserService.getUser(readDTO.getId())).thenReturn(readDTO);

        String resultJson = mockMvc
                .perform(get("/api/v1/users/{id}", readDTO.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AppUserReadDTO actualResult = objectMapper.readValue(resultJson, AppUserReadDTO.class);
        Assertions.assertThat(readDTO).isEqualToComparingFieldByField(actualResult);

        Mockito.verify(appUserService).getUser(readDTO.getId());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        AppUserReadDTO u1 = createAppUserReadDTO();
        AppUserReadDTO u2 = createAppUserReadDTO();
        AppUserReadDTO u3 = createAppUserReadDTO();

        List<AppUserReadDTO> expectedResult = List.of(u1, u2, u3);

        Mockito.when(appUserService.getAllUsers()).thenReturn(expectedResult);

        String resultJson = mockMvc
                .perform(get("/api/v1/users/"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<AppUserReadDTO> actualResult = objectMapper.readValue(resultJson, new TypeReference<>() {});
        Assert.assertEquals(expectedResult, actualResult);

        Mockito.verify(appUserService).getAllUsers();
    }

    @Test
    public void testCreateUser() throws Exception {
        AppUserReadDTO readDTO = createAppUserReadDTO();

        AppUserCreateDTO createDTO = new AppUserCreateDTO();
        createDTO.setUsername("username");
        createDTO.setChatId(2432432L);
        createDTO.setPassword("123qwerty");
        createDTO.setPasswordConfirmation("123qwerty");

        Mockito.when(appUserService.createUser(createDTO)).thenReturn(readDTO);

        String resultJson = mockMvc
                .perform(post("/api/v1/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AppUserReadDTO actualResult = objectMapper.readValue(resultJson, AppUserReadDTO.class);
        Assertions.assertThat(readDTO).isEqualToComparingFieldByField(actualResult);

        Mockito.verify(appUserService).createUser(createDTO);
    }

    @Test
    public void testPatchUser() throws Exception {
        AppUserReadDTO readDTO = createAppUserReadDTO();

        AppUserPatchDTO patchDTO = new AppUserPatchDTO();
        patchDTO.setIsBlocked(true);
        patchDTO.setUsername("new name");
        patchDTO.setChatId(233423L);

        Mockito.when(appUserService.patchUser(readDTO.getId(), patchDTO)).thenReturn(readDTO);

        String resultJson = mockMvc
                .perform(patch("/api/v1/users/{id}", readDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AppUserReadDTO actualResult = objectMapper.readValue(resultJson, AppUserReadDTO.class);
        Assertions.assertThat(readDTO).isEqualToComparingFieldByField(actualResult);

        Mockito.verify(appUserService).patchUser(readDTO.getId(), patchDTO);
    }

    @Test
    public void testDeleteUser() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/users/{id}", id))
                .andExpect(status().isOk());

        Mockito.verify(appUserService).deleteUser(id);
    }

    private AppUserReadDTO createAppUserReadDTO() {
        AppUserReadDTO dto = new AppUserReadDTO();
        dto.setId(UUID.randomUUID());
        dto.setIsBlocked(false);
        dto.setUsername("username");
        dto.setChatId(43243243L);
        return dto;
    }
}
