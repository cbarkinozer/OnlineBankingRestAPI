package com.cbarkinozer.onlinebankingrestapi.app.acc.service;

import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccAccountActivityDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccMoneyActivityRequestDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.entityservice.AccAccountActivityEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.entityservice.AccAccountEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.GenErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.GenBusinessException;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.IllegalFieldException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

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
    void shouldNotWithdraw_WhenMoneyActivityRequestDto_IsNull(){

        GenBusinessException genBusinessException = new GenBusinessException(GenErrorMessage.PARAMETER_CANNOT_BE_NULL);

        doThrow(GenBusinessException.class).when(accAccountValidationService).controlIsMoneyActivityRequestDtoNotNull(null);

        GenBusinessException result = assertThrows(GenBusinessException.class,
                () -> accAccountActivityService.withdraw(null));

        assertEquals(genBusinessException, result);
        assertEquals(genBusinessException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(genBusinessException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotWithdraw_WhenAccountId_DoesNotExist(){

        IllegalFieldException illegalFieldException = new IllegalFieldException(AccErrorMessage.ACCOUNT_NOT_FOUND);

        doThrow(IllegalFieldException.class).when(accAccountValidationService).controlIsAccountIdExist(0L);

        IllegalFieldException result = assertThrows(IllegalFieldException.class,
                () -> accAccountActivityService.withdraw(null));

        assertEquals(illegalFieldException, result);
        assertEquals(illegalFieldException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(illegalFieldException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotWithdraw_WhenAmount_IsPositive(){

        IllegalFieldException illegalFieldException = new IllegalFieldException(AccErrorMessage.ACCOUNT_NOT_FOUND);

        doThrow(IllegalFieldException.class).when(accAccountValidationService).controlIsAmountPositive(BigDecimal.valueOf(-1));

        IllegalFieldException result = assertThrows(IllegalFieldException.class,
                () -> accAccountActivityService.withdraw(null));

        assertEquals(illegalFieldException, result);
        assertEquals(illegalFieldException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(illegalFieldException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);

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

    @Test
    void shouldNotDeposit_WhenMoneyActivityRequestDto_IsNull(){

        GenBusinessException genBusinessException = new GenBusinessException(GenErrorMessage.PARAMETER_CANNOT_BE_NULL);

        doThrow(GenBusinessException.class).when(accAccountValidationService).controlIsMoneyActivityRequestDtoNotNull(null);

        GenBusinessException result = assertThrows(GenBusinessException.class,
                () -> accAccountActivityService.deposit(null));

        assertEquals(genBusinessException, result);
        assertEquals(genBusinessException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(genBusinessException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotDeposit_WhenAccountId_DoesNotExist(){

        AccMoneyActivityRequestDto accMoneyActivityRequestDto = mock(AccMoneyActivityRequestDto.class);

        IllegalFieldException illegalFieldException = new IllegalFieldException(AccErrorMessage.ACCOUNT_NOT_FOUND);

        when(accMoneyActivityRequestDto.getAccountId()).thenReturn(0L);

        doThrow(IllegalFieldException.class).when(accAccountValidationService).controlIsAccountIdExist(0L);

        IllegalFieldException result = assertThrows(IllegalFieldException.class,
                () -> accAccountActivityService.deposit(accMoneyActivityRequestDto));

        assertEquals(illegalFieldException, result);
        assertEquals(illegalFieldException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(illegalFieldException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotDeposit_WhenAmount_IsNotPositive(){


        AccMoneyActivityRequestDto accMoneyActivityRequestDto = mock(AccMoneyActivityRequestDto.class);

        IllegalFieldException illegalFieldException = new IllegalFieldException(AccErrorMessage.ACCOUNT_NOT_FOUND);

        when(accMoneyActivityRequestDto.getAmount()).thenReturn(BigDecimal.valueOf(-1));

        doThrow(IllegalFieldException.class).when(accAccountValidationService).controlIsAmountPositive(BigDecimal.valueOf(-1));

        IllegalFieldException result = assertThrows(IllegalFieldException.class,
                () -> accAccountActivityService.deposit(accMoneyActivityRequestDto));

        assertEquals(illegalFieldException, result);
        assertEquals(illegalFieldException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(illegalFieldException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);

    }
}