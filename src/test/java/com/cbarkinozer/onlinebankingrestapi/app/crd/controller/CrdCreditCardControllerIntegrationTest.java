package com.cbarkinozer.onlinebankingrestapi.app.crd.controller;

import com.cbarkinozer.onlinebankingrestapi.OnlinebankingrestapiApplication;
import com.cbarkinozer.onlinebankingrestapi.app.config.H2TestProfileJPAConfig;
import com.cbarkinozer.onlinebankingrestapi.app.crd.dto.*;
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
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {OnlinebankingrestapiApplication.class, H2TestProfileJPAConfig.class})
class CrdCreditCardControllerIntegrationTest extends BaseTest {


    private static final String BASE_PATH = "/api/v1/credit-cards";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void findAllCreditCards() throws Exception{

        MvcResult result = mockMvc.perform(
                get(BASE_PATH).content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void findCreditCardById() throws Exception {

        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/1").content("1L").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void findCreditCardActivityByAmountInterval() throws Exception {

        MvcResult result = mockMvc.perform(
                get(BASE_PATH+ "/find-activity-by-amount-interval").param("min", "100")
                        .param("max", "200").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void findCreditCardActivityBetweenDates() throws Exception{

        MvcResult result = mockMvc.perform(
                get(BASE_PATH+"/1L"+"/activities").content("1L").param("startDate", "2022-05-15")
                .param("endDate", "2022-05-15").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void getCardActivityAnalysis() throws  Exception{

        MvcResult result = mockMvc.perform(
                get(BASE_PATH+"/get-card-activity-analysis"+"/1L").content("1L").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);

    }

    @Test
    void getCardDetails() throws Exception {

        MvcResult result = mockMvc.perform(
                get(BASE_PATH+"/1L"+"/cardDetails").content("1L").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void saveCreditCard() throws Exception{

        CrdCreditCardSaveDto crdCreditCardSaveDto = new CrdCreditCardSaveDto();
        crdCreditCardSaveDto.setCutOffDay(15);
        crdCreditCardSaveDto.setEarning(BigDecimal.valueOf(9000));

        String content = objectMapper.writeValueAsString(crdCreditCardSaveDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH+"/save-credit-card").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void spendMoney() throws Exception{

        CrdCreditCardSpendDto crdCreditCardSpendDto = new CrdCreditCardSpendDto();
        crdCreditCardSpendDto.setCardNo(1L);
        crdCreditCardSpendDto.setAmount(BigDecimal.valueOf(100));
        crdCreditCardSpendDto.setDescription("Here some money");
        crdCreditCardSpendDto.setCvvNo(111L);
        crdCreditCardSpendDto.setExpireDate(LocalDate.now());

        String content = objectMapper.writeValueAsString(crdCreditCardSpendDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH+"/save-credit-card").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void refundMoney() throws Exception{

        MvcResult result = mockMvc.perform(
                post(BASE_PATH+"/refund"+"/1L").content("1L").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void receivePayment() throws Exception{

        CrdCreditCardPaymentDto crdCreditCardPaymentDto = new CrdCreditCardPaymentDto();
        crdCreditCardPaymentDto.setCrdCreditCardId(1L);
        crdCreditCardPaymentDto.setAmount(BigDecimal.valueOf(100));

        String content = objectMapper.writeValueAsString(crdCreditCardPaymentDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH+"/receive-payment").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void cancelCreditCard() throws Exception{

        MvcResult result = mockMvc.perform(
                patch(BASE_PATH+"/cancel"+"/1L").content("1L").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }
}