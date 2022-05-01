package com.cbarkinozer.onlinebankingrestapi.app.acc.service;

import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccAccountActivityDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccMoneyActivityRequestDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccMoneyTransferDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccMoneyTransferSaveDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccMoneyTransfer;
import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.entityservice.AccMoneyTransferEntityService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccMoneyTransferServiceTest {

    @Mock
    private AccMoneyTransferEntityService accMoneyTransferEntityService;

    @Mock
    private AccAccountActivityService accAccountActivityService;

    @Mock
    private AccAccountValidationService accAccountValidationService;

    @InjectMocks
    private AccMoneyTransferService accMoneyTransferService;

    @Test
    void transferMoney() {

        AccMoneyTransfer accMoneyTransfer = mock(AccMoneyTransfer.class);
        AccMoneyTransferSaveDto accMoneyTransferSaveDto = mock(AccMoneyTransferSaveDto.class);

        when(accMoneyTransfer.getAmount()).thenReturn(BigDecimal.valueOf(100));

        accMoneyTransferService.transferMoney(accMoneyTransferSaveDto);

        verify(accMoneyTransferEntityService.save(accMoneyTransfer));
    }

    @Test
    void shouldNotTransferMoney_WhenMoneyTransferSaveDto_IsNull(){

        GenBusinessException genBusinessException = new GenBusinessException(GenErrorMessage.PARAMETER_CANNOT_BE_NULL);

        doThrow(GenBusinessException.class).when(accAccountValidationService).controlIsMoneyTransferSaveDtoIsNull(null);

        GenBusinessException result = assertThrows(GenBusinessException.class,
                () -> accMoneyTransferService.transferMoney(null));

        assertEquals(genBusinessException, result);
        assertEquals(genBusinessException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(genBusinessException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotTransferMoney_WhenAccountId_DoesNotExist(){

        AccMoneyTransferSaveDto accMoneyTransferSaveDto = mock(AccMoneyTransferSaveDto.class);

        IllegalFieldException illegalFieldException = new IllegalFieldException(AccErrorMessage.ACCOUNT_NOT_FOUND);

        when(accMoneyTransferSaveDto.getAccountIdFrom()).thenReturn(0L);
        when(accMoneyTransferSaveDto.getAccountIdTo()).thenReturn(0L);

        doThrow(IllegalFieldException.class).when(accAccountValidationService).controlIsAccountIdExist(0L);

        IllegalFieldException result = assertThrows(IllegalFieldException.class,
                () -> accMoneyTransferService.transferMoney(accMoneyTransferSaveDto));

        assertEquals(illegalFieldException, result);
        assertEquals(illegalFieldException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(illegalFieldException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotDeposit_WhenAmount_IsPositive(){

        AccMoneyActivityRequestDto accMoneyActivityRequestDto = mock(AccMoneyActivityRequestDto.class);

        IllegalFieldException illegalFieldException = new IllegalFieldException(AccErrorMessage.ACCOUNT_NOT_FOUND);

        when(accMoneyActivityRequestDto.getAmount()).thenReturn(BigDecimal.valueOf(0L));

        doThrow(IllegalFieldException.class).when(accAccountValidationService).controlIsAmountPositive(BigDecimal.valueOf(-1));

        IllegalFieldException result = assertThrows(IllegalFieldException.class,
                () -> accAccountActivityService.deposit(accMoneyActivityRequestDto));

        assertEquals(illegalFieldException, result);
        assertEquals(illegalFieldException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(illegalFieldException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);

    }
}