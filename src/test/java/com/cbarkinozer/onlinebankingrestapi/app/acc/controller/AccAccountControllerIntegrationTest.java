package com.cbarkinozer.onlinebankingrestapi.app.acc.controller;

import com.cbarkinozer.onlinebankingrestapi.OnlinebankingrestapiApplication;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccAccountSaveDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccMoneyActivityRequestDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccMoneyTransferSaveDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccAccountType;
import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccCurrencyType;
import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccMoneyTransferType;
import com.cbarkinozer.onlinebankingrestapi.app.config.H2TestProfileJPAConfig;
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

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {OnlinebankingrestapiApplication.class, H2TestProfileJPAConfig.class})
class AccAccountControllerIntegrationTest extends BaseTest {

    private static final String BASE_PATH = "/api/v1/accounts";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void findAllAccounts() throws Exception{

        MvcResult result = mockMvc.perform(
                get(BASE_PATH).content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void findAccountById() throws Exception {

        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/1").content("1L").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void findAccountByCustomerId() throws Exception{

        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/1").content("1L").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void saveAccount() throws Exception {

        AccAccountSaveDto accAccountSaveDto = new AccAccountSaveDto();
        accAccountSaveDto.setAccountType(AccAccountType.DEPOSIT);
        accAccountSaveDto.setCurrentBalance(BigDecimal.valueOf(100));
        accAccountSaveDto.setCurrencyType(AccCurrencyType.TL);

        String content = objectMapper.writeValueAsString(accAccountSaveDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH+"/save-account").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void cancelAccount() throws Exception {

        MvcResult result = mockMvc.perform(
                put(BASE_PATH+"/cancel-account"+"/1").content("1L").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void transferMoney() throws Exception {

        AccMoneyTransferSaveDto accMoneyTransferSaveDto = new AccMoneyTransferSaveDto();
        accMoneyTransferSaveDto.setAccountIdFrom(1L);
        accMoneyTransferSaveDto.setAccountIdTo(2L);
        accMoneyTransferSaveDto.setTransferType(AccMoneyTransferType.DUE);
        accMoneyTransferSaveDto.setAmount(BigDecimal.valueOf(100));
        accMoneyTransferSaveDto.setDescription("Here some test money");

        String content = objectMapper.writeValueAsString(accMoneyTransferSaveDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH+"/transfer-money/").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void withdraw() throws Exception {

        AccMoneyActivityRequestDto accMoneyActivityRequestDto = new AccMoneyActivityRequestDto();
        accMoneyActivityRequestDto.setAccountId(1L);
        accMoneyActivityRequestDto.setAmount(BigDecimal.valueOf(100));

        String content = objectMapper.writeValueAsString(accMoneyActivityRequestDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH+"/withdraw/").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void deposit() throws Exception {

        AccMoneyActivityRequestDto accMoneyActivityRequestDto = new AccMoneyActivityRequestDto();
        accMoneyActivityRequestDto.setAccountId(1L);
        accMoneyActivityRequestDto.setAmount(BigDecimal.valueOf(100));

        String content = objectMapper.writeValueAsString(accMoneyActivityRequestDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH+"/deposit/").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }
}