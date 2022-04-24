package com.cbarkinozer.onlinebankingrestapi.app.cus.controller;

import com.cbarkinozer.onlinebankingrestapi.OnlinebankingrestapiApplication;
import com.cbarkinozer.onlinebankingrestapi.app.config.H2TestProfileJPAConfig;
import com.cbarkinozer.onlinebankingrestapi.app.cus.dto.CusCustomerSaveDto;
import com.cbarkinozer.onlinebankingrestapi.app.cus.dto.CusCustomerUpdateDto;
import com.cbarkinozer.onlinebankingrestapi.app.gen.BaseTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {OnlinebankingrestapiApplication.class, H2TestProfileJPAConfig.class})
public class CusCustomerControllerIntegrationTest extends BaseTest {

    private static final String BASE_PATH = "/api/v1/customers";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void shouldFindAllCustomers() throws Exception {
        MvcResult result = mockMvc.perform(
                get(BASE_PATH).content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldFindCustomerById() throws Exception {
        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/7").content("7L").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldNotFindCustomerByIdWhenUsrUserIdIsNotExist() throws Exception {
        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/9").content("9L").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldSaveCustomer() throws Exception {

        CusCustomerSaveDto cusCustomerSaveDto = new CusCustomerSaveDto();
        cusCustomerSaveDto.setName("Test");
        cusCustomerSaveDto.setSurname("Test");
        cusCustomerSaveDto.setIdentityNo(11111111111L);

        String content = objectMapper.writeValueAsString(cusCustomerSaveDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH+"/save-customer/").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldNotSaveCustomer_WhenIdentityNo_IsNotUnique() throws Exception {

        CusCustomerSaveDto cusCustomerSaveDto = new CusCustomerSaveDto();
        cusCustomerSaveDto.setName("Test");
        cusCustomerSaveDto.setSurname("Test");
        cusCustomerSaveDto.setIdentityNo(11111111111L);

        String content = objectMapper.writeValueAsString(cusCustomerSaveDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH+"/save-customer/").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isConflict()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldUpdateCustomer() throws Exception {

        CusCustomerUpdateDto cusCustomerUpdateDto = new CusCustomerUpdateDto();
        cusCustomerUpdateDto.setId(2L);
        cusCustomerUpdateDto.setName("Test");
        cusCustomerUpdateDto.setSurname("Test");
        cusCustomerUpdateDto.setIdentityNo(11111111111L);
        cusCustomerUpdateDto.setPassword("test1234");

        String content = objectMapper.writeValueAsString(cusCustomerUpdateDto);

        MvcResult result = mockMvc.perform(
                put(BASE_PATH +"/update-customer/").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldNotUpdateCustomer_WhenFields_AreNull() throws Exception {

        CusCustomerUpdateDto cusCustomerUpdateDto = new CusCustomerUpdateDto();

        cusCustomerUpdateDto.setName(" ");
        cusCustomerUpdateDto.setSurname("");
        cusCustomerUpdateDto.setIdentityNo(null);
        cusCustomerUpdateDto.setPassword(null);

        String content = objectMapper.writeValueAsString(cusCustomerUpdateDto);

        MvcResult result = mockMvc.perform(
                put(BASE_PATH +"/update-customer/").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isConflict()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldNotUpdateCustomer_WhenIdentityNo_IsNotUnique() throws Exception {

        CusCustomerUpdateDto cusCustomerUpdateDto = new CusCustomerUpdateDto();
        cusCustomerUpdateDto.setName("Test4");
        cusCustomerUpdateDto.setSurname("Test4");
        cusCustomerUpdateDto.setIdentityNo(11111111111L);
        cusCustomerUpdateDto.setPassword("123456");

        String content = objectMapper.writeValueAsString(cusCustomerUpdateDto);

        MvcResult result = mockMvc.perform(
                put(BASE_PATH + "/update-customer/").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isConflict()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldDeleteCustomer() throws Exception {

        MvcResult result = mockMvc.perform(
                delete(BASE_PATH +"/delete-customer"+"/1").content("1").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldNoDeleteCustomer_WhenId_DoesNotExist() throws Exception {

        MvcResult result = mockMvc.perform(
                delete(BASE_PATH +"/delete-customer"+"/9999").content("9999").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

}
