package com.cbarkinozer.onlinebankingrestapi.app.crd.controller;

import com.cbarkinozer.onlinebankingrestapi.app.crd.dto.*;
import com.cbarkinozer.onlinebankingrestapi.app.crd.enums.CrdCreditCardActivityType;
import com.cbarkinozer.onlinebankingrestapi.app.crd.service.CrdCreditCardActivityService;
import com.cbarkinozer.onlinebankingrestapi.app.crd.service.CrdCreditCardService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.dto.RestResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrdCreditCardControllerTest {

    @Mock
    private CrdCreditCardService crdCreditCardService;

    @Mock
    private CrdCreditCardActivityService crdCreditCardActivityService;

    @InjectMocks
    private CrdCreditCardController crdCreditCardController;

    @Test
    void shouldFindAllCreditCards() {

        List<CrdCreditCardDto> dummyCrdCreditCardDtoList = createDummyCrdCreditCardDtoList();

        when(crdCreditCardService.findAllCreditCards()).thenReturn(dummyCrdCreditCardDtoList);

        ResponseEntity<RestResponse<List<CrdCreditCardDto>>> result = crdCreditCardController.findAllCreditCards();

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),dummyCrdCreditCardDtoList);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    private CrdCreditCardDto createDummyCrdCreditCardDto(){

        CrdCreditCardDto crdCreditCardDto = mock(CrdCreditCardDto.class);

        crdCreditCardDto.setId(1L);
        crdCreditCardDto.setCardNo(1111111111111111L);
        crdCreditCardDto.setAvailableCardLimit(BigDecimal.valueOf(90000));
        crdCreditCardDto.setCurrentDebt(BigDecimal.valueOf(0));
        crdCreditCardDto.setCusCustomerId(1L);
        crdCreditCardDto.setCutoffDate(LocalDate.now().plusDays(30));
        crdCreditCardDto.setCvvNo(111L);
        crdCreditCardDto.setDueDate(LocalDate.now().plusYears(4));
        crdCreditCardDto.setCancelDate(null);

        return  crdCreditCardDto;
    }

    private List<CrdCreditCardDto> createDummyCrdCreditCardDtoList(){

        List<CrdCreditCardDto>  crdCreditCardDtoList = new ArrayList<>();

        CrdCreditCardDto dummyCrdCreditCardDto = createDummyCrdCreditCardDto();
        crdCreditCardDtoList.add(dummyCrdCreditCardDto);

        return crdCreditCardDtoList;
    }

    @Test
    void shouldFindCreditCardById() {

        CrdCreditCardDto dummyCrdCreditCardDto = createDummyCrdCreditCardDto();

        when(crdCreditCardService.findCreditCardById(1L)).thenReturn(dummyCrdCreditCardDto);

        ResponseEntity<RestResponse<CrdCreditCardDto>> result = crdCreditCardController.findCreditCardById(1L);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),dummyCrdCreditCardDto);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldFindCreditCardActivityByAmountInterval() {

        BigDecimal min = BigDecimal.ONE;
        BigDecimal max = BigDecimal.valueOf(2000);

        List<CrdCreditCardActivityDto> crdCreditCardActivityDtoList = createDummyCrdCreditCardActivityDtoList();

        when(crdCreditCardActivityService.findCreditCardActivityByAmountInterval(min,max)).thenReturn(crdCreditCardActivityDtoList);

        ResponseEntity<RestResponse<List<CrdCreditCardActivityDto>>> result = crdCreditCardController.findCreditCardActivityByAmountInterval(min,max);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),crdCreditCardActivityDtoList);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    private List<CrdCreditCardActivityDto> createDummyCrdCreditCardActivityDtoList() {

        List<CrdCreditCardActivityDto> crdCreditCardActivityDtoList = new ArrayList<>();
        crdCreditCardActivityDtoList.add(createDummyCrdCreditCardActivityDto());

        return crdCreditCardActivityDtoList;
    }

    @Test
    void shouldFindCreditCardActivityBetweenDates() {

        Long id = 1L;
        LocalDate startDate = LocalDate.now().minusYears(4);
        LocalDate endDate = LocalDate.now().plusYears(4);

        List<CrdCreditCardActivityDto> crdCreditCardActivityDtoList = createDummyCrdCreditCardActivityDtoList();

        when(crdCreditCardService.findCreditCardActivityBetweenDates(id, startDate,endDate)).thenReturn(crdCreditCardActivityDtoList);

        ResponseEntity<RestResponse<List<CrdCreditCardActivityDto>>> result =
                crdCreditCardController.findCreditCardActivityBetweenDates(id, startDate,endDate);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),crdCreditCardActivityDtoList);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldGetCardActivityAnalysis() {
    }

    @Test
    void shouldGetCardDetails() {

        CrdCreditCardDetailsDto crdCreditCardDetailsDto = mock(CrdCreditCardDetailsDto.class);

        when(crdCreditCardService.getCardDetails(1L)).thenReturn(crdCreditCardDetailsDto);

        ResponseEntity<RestResponse<CrdCreditCardDetailsDto>> result = crdCreditCardController.getCardDetails(1L);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertNotNull(result.getBody().getData());
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldSaveCreditCard() {

        CrdCreditCardSaveDto dummyCrdCreditCardSaveDto = createDummyCrdCreditCardSaveDto();
        CrdCreditCardDto dummyCrdCreditCardDto = createDummyCrdCreditCardDto();

        when(crdCreditCardService.saveCreditCard(dummyCrdCreditCardSaveDto)).thenReturn(dummyCrdCreditCardDto);

        ResponseEntity<RestResponse<MappingJacksonValue>> result = crdCreditCardController.saveCreditCard(dummyCrdCreditCardSaveDto);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertNotNull(result.getBody().getData());
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    private CrdCreditCardSaveDto createDummyCrdCreditCardSaveDto(){

        CrdCreditCardSaveDto crdCreditCardSaveDto = mock(CrdCreditCardSaveDto.class);

        crdCreditCardSaveDto.setEarning(BigDecimal.valueOf(9000));
        crdCreditCardSaveDto.setCutOffDay(15);

        return  crdCreditCardSaveDto;
    }

    @Test
    void shouldSpendMoney() {

        CrdCreditCardSpendDto dummyCrdCreditCardSpendDto = createDummyCrdCreditCardSpendDto();
        CrdCreditCardActivityDto dummyCrdCreditCardActivityDto = createDummyCrdCreditCardActivityDto();

        when(crdCreditCardService.spendMoney(dummyCrdCreditCardSpendDto)).thenReturn(dummyCrdCreditCardActivityDto);

        ResponseEntity<RestResponse<CrdCreditCardActivityDto>> result = crdCreditCardController.spendMoney(dummyCrdCreditCardSpendDto);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertNotNull(result.getBody().getData());
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    private CrdCreditCardActivityDto createDummyCrdCreditCardActivityDto() {

        CrdCreditCardActivityDto crdCreditCardActivityDto = mock(CrdCreditCardActivityDto.class);

        crdCreditCardActivityDto.setId(1L);
        crdCreditCardActivityDto.setCrdCreditCardId(1L);
        crdCreditCardActivityDto.setCardActivityType(CrdCreditCardActivityType.SPEND);
        crdCreditCardActivityDto.setAmount(BigDecimal.valueOf(100));
        crdCreditCardActivityDto.setDescription("spend");
        crdCreditCardActivityDto.setTransactionDate(LocalDateTime.now());

        return  crdCreditCardActivityDto;
    }

    private CrdCreditCardSpendDto createDummyCrdCreditCardSpendDto() {

        CrdCreditCardSpendDto crdCreditCardSpendDto = mock(CrdCreditCardSpendDto.class);

        crdCreditCardSpendDto.setCardNo(1L);
        crdCreditCardSpendDto.setExpireDate(LocalDate.now().plusYears(4));
        crdCreditCardSpendDto.setCvvNo(111L);
        crdCreditCardSpendDto.setDescription("spend");
        crdCreditCardSpendDto.setAmount(BigDecimal.valueOf(100));

        return  crdCreditCardSpendDto;
    }

    @Test
    void shouldRefundMoney() {

        CrdCreditCardActivityDto dummyCrdCreditCardActivityDto = createDummyCrdCreditCardActivityDto();

        when(crdCreditCardService.refundMoney(1L)).thenReturn(dummyCrdCreditCardActivityDto);

        ResponseEntity<RestResponse<CrdCreditCardActivityDto>> result = crdCreditCardController.refundMoney(1L);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertNotNull(result.getBody().getData());
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldReceivePayment() {

        CrdCreditCardPaymentDto dummyCrdCreditCardPaymentDto = createDummyCrdCreditCardPaymentDto();
        CrdCreditCardActivityDto dummyCrdCreditCardActivityDto = createDummyCrdCreditCardActivityDto();

        when(crdCreditCardService.receivePayment(dummyCrdCreditCardPaymentDto)).thenReturn(dummyCrdCreditCardActivityDto);

        ResponseEntity<RestResponse<CrdCreditCardActivityDto>> result = crdCreditCardController.receivePayment(dummyCrdCreditCardPaymentDto);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertNotNull(result.getBody().getData());
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    private CrdCreditCardPaymentDto createDummyCrdCreditCardPaymentDto() {
        CrdCreditCardPaymentDto crdCreditCardPaymentDto = mock(CrdCreditCardPaymentDto.class);

        crdCreditCardPaymentDto.setCrdCreditCardId(1L);
        crdCreditCardPaymentDto.setAmount(BigDecimal.valueOf(9000));

        return crdCreditCardPaymentDto;
    }

    @Test
    void shouldCancelCreditCard() {

        doNothing().when(crdCreditCardService.findCreditCardById(1L));

        ResponseEntity<RestResponse<CrdCreditCardDto>> result = crdCreditCardController.findCreditCardById(1L);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertNull(result.getBody().getData());
        assertNull(result.getBody().getMessage());
    }
}