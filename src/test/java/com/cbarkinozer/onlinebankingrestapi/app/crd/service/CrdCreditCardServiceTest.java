package com.cbarkinozer.onlinebankingrestapi.app.crd.service;

import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccAccountActivityDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccAccountDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccAccountSaveDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccMoneyActivityRequestDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccAccount;
import com.cbarkinozer.onlinebankingrestapi.app.crd.dto.*;
import com.cbarkinozer.onlinebankingrestapi.app.crd.entity.CrdCreditCard;
import com.cbarkinozer.onlinebankingrestapi.app.crd.entity.CrdCreditCardActivity;
import com.cbarkinozer.onlinebankingrestapi.app.crd.service.entityservice.CrdCreditCardActivityEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.crd.service.entityservice.CrdCreditCardEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrdCreditCardServiceTest {

    @Mock
    private  CrdCreditCardEntityService crdCreditCardEntityService;

    @Mock
    private  CrdCreditCardValidationService crdCreditCardValidationService;

    @Mock
    private  CrdCreditCardActivityEntityService crdCreditCardActivityEntityService;

    @InjectMocks
    private CrdCreditCardService crdCreditCardService;

    @Test
    void shouldFindAllCreditCards() {

        CrdCreditCard crdCreditCard = mock(CrdCreditCard.class);
        List<CrdCreditCard> crdCreditCardList = new ArrayList<>();
        crdCreditCardList.add(crdCreditCard);

        when(crdCreditCardEntityService.findAllActiveCreditCardList()).thenReturn(crdCreditCardList);

        List<CrdCreditCardDto> result = crdCreditCardService.findAllCreditCards();

        assertEquals(1, result.size());
    }

    @Test
    void shouldFindCreditCardById() {

        Long id = 1L;

        CrdCreditCard crdCreditCard = mock(CrdCreditCard.class);
        when(crdCreditCard.getId()).thenReturn(id);

        when(crdCreditCardEntityService.getByIdWithControl(id)).thenReturn(crdCreditCard);

        CrdCreditCardDto crdCreditCardDto = crdCreditCardService.findCreditCardById(id);

        assertEquals(id, crdCreditCardDto.getId());
    }

    @Test
    void shouldGetCardDetails() {

        CrdCreditCardDetailsDto crdCreditCardDetailsDto = mock(CrdCreditCardDetailsDto.class);

        when(crdCreditCardEntityService.getCreditCardDetails(1L)).thenReturn(crdCreditCardDetailsDto);

        CrdCreditCardDetailsDto result = crdCreditCardService.getCardDetails(1L);

        assertNotNull(result);
    }

    @Test
    void shouldSaveCreditCard() {

        CrdCreditCardSaveDto crdCreditCardSaveDto = mock(CrdCreditCardSaveDto.class);

        CrdCreditCard crdCreditCard = mock(CrdCreditCard.class);

        when(crdCreditCard.getId()).thenReturn(1L);

        when(crdCreditCardEntityService.save(any())).thenReturn(crdCreditCard);

        CrdCreditCardDto result = crdCreditCardService.saveCreditCard(crdCreditCardSaveDto);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldCancelCreditCard() {

        CrdCreditCard crdCreditCard = mock(CrdCreditCard.class);

        when(crdCreditCardEntityService.getByIdWithControl(anyLong())).thenReturn(crdCreditCard);

        crdCreditCardService.cancelCreditCard(anyLong());

        verify(crdCreditCardEntityService).getByIdWithControl(anyLong());
        verify(crdCreditCardEntityService).save(any());
    }

    @Test
    void shouldFindCreditCardActivityBetweenDates() {

        CrdCreditCardActivity crdCreditCardActivity = mock(CrdCreditCardActivity.class);
        List<CrdCreditCardActivity> crdCreditCardActivityList = new ArrayList<>();
        crdCreditCardActivityList.add(crdCreditCardActivity);

        Long id =1L;
        LocalDateTime startDate =LocalDateTime.now().minusDays(1);
        LocalDateTime endDate =LocalDateTime.now().plusMonths(1);

        when(crdCreditCardActivityEntityService.findCreditCardActivityBetweenDates(
                id,startDate,endDate)
        ).thenReturn(crdCreditCardActivityList);

        List<CrdCreditCardActivityDto> result = crdCreditCardService.findCreditCardActivityBetweenDates(
                id,LocalDate.now(),LocalDate.now().plusMonths(24));

        assertEquals(1, result.size());
    }

    @Test
    void shouldSpendMoney() {

        CrdCreditCardSpendDto crdCreditCardSpendDto = mock(CrdCreditCardSpendDto.class);
        CrdCreditCardActivityDto crdCreditCardActivityDto = mock(CrdCreditCardActivityDto.class);

        when(crdCreditCardSpendDto.getAmount()).thenReturn(BigDecimal.valueOf(200));
        when(crdCreditCardActivityDto.getAmount()).thenReturn(BigDecimal.valueOf(200));

        CrdCreditCardActivityDto result = crdCreditCardService.spendMoney(crdCreditCardSpendDto);

        assertEquals(BigDecimal.valueOf(200),result.getAmount());
    }

    @Test
    void shouldRefundMoney() {

        CrdCreditCardActivityDto crdCreditCardActivityDto = mock(CrdCreditCardActivityDto.class);

        when(crdCreditCardActivityDto.getAmount()).thenReturn(BigDecimal.valueOf(200));

        CrdCreditCardActivityDto result = crdCreditCardService.refundMoney(1L);

        assertEquals(BigDecimal.valueOf(200),result.getAmount());
    }

    @Test
    void shouldReceivePayment() {

        CrdCreditCardPaymentDto crdCreditCardPaymentDto = mock(CrdCreditCardPaymentDto.class);

        when(crdCreditCardPaymentDto.getAmount()).thenReturn(BigDecimal.valueOf(200));

        CrdCreditCardActivityDto result = crdCreditCardService.receivePayment(crdCreditCardPaymentDto);

        assertEquals(BigDecimal.valueOf(200),result.getAmount());

    }
}