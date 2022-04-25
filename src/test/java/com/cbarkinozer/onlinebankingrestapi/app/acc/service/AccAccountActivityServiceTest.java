package com.cbarkinozer.onlinebankingrestapi.app.acc.service;

import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccAccountActivityDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccMoneyActivityDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccMoneyActivityRequestDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccAccountActivity;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.entityservice.AccAccountActivityEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.entityservice.AccAccountEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccAccountActivityServiceTest {

    @Mock
    private AccAccountEntityService accAccountEntityService;

    @Mock
    private AccAccountActivityEntityService accAccountActivityEntityService;

    @Mock
    private AccAccountValidationService accAccountValidationService;

    @InjectMocks
    private AccAccountActivityService accAccountActivityService;

    @Test
    void shouldWithdraw() {

        AccMoneyActivityRequestDto accMoneyActivityRequestDto = mock(AccMoneyActivityRequestDto.class);
        AccAccountActivityDto accAccountActivityDto = mock(AccAccountActivityDto.class);

        when(accAccountActivityDto.getCurrentBalance()).thenReturn(BigDecimal.valueOf(200));
        when(accMoneyActivityRequestDto.getAmount()).thenReturn(BigDecimal.valueOf(100));

        AccAccountActivityDto result = accAccountActivityService.withdraw(accMoneyActivityRequestDto);

        assertEquals(BigDecimal.valueOf(100),result.getCurrentBalance());
    }

    @Test
    void shouldDeposit() {

        AccMoneyActivityRequestDto accMoneyActivityRequestDto = mock(AccMoneyActivityRequestDto.class);
        AccAccountActivityDto accAccountActivityDto = mock(AccAccountActivityDto.class);

        when(accAccountActivityDto.getCurrentBalance()).thenReturn(BigDecimal.valueOf(100));
        when(accMoneyActivityRequestDto.getAmount()).thenReturn(BigDecimal.valueOf(100));

        AccAccountActivityDto result = accAccountActivityService.deposit(accMoneyActivityRequestDto);

        assertEquals(BigDecimal.valueOf(200),result.getCurrentBalance());
    }
}