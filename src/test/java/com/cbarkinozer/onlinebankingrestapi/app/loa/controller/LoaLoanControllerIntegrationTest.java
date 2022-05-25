package com.cbarkinozer.onlinebankingrestapi.app.loa.controller;

import com.cbarkinozer.onlinebankingrestapi.OnlinebankingrestapiApplication;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccAccountSaveDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccAccountType;
import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccCurrencyType;
import com.cbarkinozer.onlinebankingrestapi.app.config.H2TestProfileJPAConfig;
import com.cbarkinozer.onlinebankingrestapi.app.gen.BaseTest;
import com.cbarkinozer.onlinebankingrestapi.app.loa.dto.LoaApplyLoanDto;
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
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {OnlinebankingrestapiApplication.class, H2TestProfileJPAConfig.class})
class LoaLoanControllerIntegrationTest extends BaseTest {

    private static final String BASE_PATH = "/api/v1/loans";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void calculateLoan() throws Exception {

        MvcResult result = mockMvc.perform(
                get(BASE_PATH+"/calculate-loan").param(String.valueOf(24)).param(String.valueOf(3000)).content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void calculateLateFee() throws Exception {

        MvcResult result = mockMvc.perform(
                get(BASE_PATH+"/calculate-late-fee"+"1").content("1L").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void findLoanById() throws Exception{
        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/1").content("1L").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void applyLoan() throws Exception {

        LoaApplyLoanDto loaApplyLoanDto = new LoaApplyLoanDto();
        loaApplyLoanDto.setInstallmentCount(24);
        loaApplyLoanDto.setPrincipalLoanAmount(BigDecimal.valueOf(3000));
        loaApplyLoanDto.setMonthlySalary(BigDecimal.valueOf(9000));

        String content = objectMapper.writeValueAsString(loaApplyLoanDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH+"/apply-loan").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void payInstallment() throws Exception{

        MvcResult result = mockMvc.perform(
                post(BASE_PATH+"/pay_installment"+"1").content("1L").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void payLoanOff() throws Exception{

        MvcResult result = mockMvc.perform(
                delete(BASE_PATH+"/pay_loan_off"+"1").content("1L").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }
}