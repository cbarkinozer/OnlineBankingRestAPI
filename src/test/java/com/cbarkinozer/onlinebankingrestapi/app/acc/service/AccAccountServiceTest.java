package com.cbarkinozer.onlinebankingrestapi.app.acc.service;

import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccAccountDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccAccountSaveDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccAccount;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.entityservice.AccAccountEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.IllegalFieldException;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.ItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccAccountServiceTest {

    @Mock
    private AccAccountEntityService accAccountEntityService;

    @Mock
    private AccAccountValidationService accAccountValidationService;

    @InjectMocks
    private AccAccountService accAccountService;

    @Test
    void shouldFindAllAccounts() {

        AccAccount accAccount = mock(AccAccount.class);
        List<AccAccount> accAccountList = new ArrayList<>();
        accAccountList.add(accAccount);

        when(accAccountEntityService.findAllActiveAccounts()).thenReturn(accAccountList);

        List<AccAccountDto> result = accAccountService.findAllAccounts();

        assertEquals(1, result.size());
    }

    @Test
    void shouldFindAllAccounts_WhenAccountList_IsEmpty() {

        List<AccAccount> accAccountList = new ArrayList<>();

        when(accAccountEntityService.findAllActiveAccounts()).thenReturn(accAccountList);

        List<AccAccountDto> result = accAccountService.findAllAccounts();

        assertEquals(0, result.size());
    }

    @Test
    void shouldFindAccountById() {

        Long id = 1L;

        AccAccount accAccount = mock(AccAccount.class);
        when(accAccount.getId()).thenReturn(id);

        when(accAccountEntityService.getByIdWithControl(id)).thenReturn(accAccount);

        AccAccountDto accAccountDto = accAccountService.findAccountById(id);

        assertEquals(id, accAccountDto.getId());
    }

    @Test
    void shouldNotFindProductById_WhenId_DoesNotExist(){

        when(accAccountEntityService.getByIdWithControl(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> accAccountService.findAccountById(anyLong()));

        verify(accAccountEntityService).getByIdWithControl(anyLong());
    }

    @Test
    void shouldFindAccountByCustomerId() {

        AccAccount accAccount = mock(AccAccount.class);
        List<AccAccount> accAccountList = new ArrayList<AccAccount>();
        accAccountList.add(accAccount);

        when(accAccountEntityService.findAccountByCustomerId(accAccount.getCustomerId()))
                .thenReturn(accAccountList);

        List<AccAccountDto> result = accAccountService.findAccountByCustomerId(accAccount.getCustomerId());

        assertEquals(1, result.size());
    }

    @Test
    void shouldNotFindAccountById_WhenId_DoesNotExist(){

        when(accAccountEntityService.getByIdWithControl(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> accAccountService.findAccountById(anyLong()));

        verify(accAccountEntityService).getByIdWithControl(anyLong());
    }

    @Test
    void shouldSaveAccount() {

        AccAccountSaveDto accAccountSaveDto = mock(AccAccountSaveDto.class);

        AccAccount accAccount = mock(AccAccount.class);

        when(accAccount.getId()).thenReturn(1L);

        when(accAccountEntityService.save(any())).thenReturn(accAccount);

        AccAccountDto result = accAccountService.saveAccount(accAccountSaveDto);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldNotSaveAccount_WhenCustomer_DoesNotExist(){

        AccAccountSaveDto accAccountSaveDto = mock(AccAccountSaveDto.class);
        AccAccount accAccount = mock(AccAccount.class);

        doThrow(IllegalFieldException.class).when(accAccountValidationService)
                .controlIsCustomerExist(accAccount.getCustomerId());

        assertThrows(IllegalFieldException.class, () -> accAccountService.saveAccount(accAccountSaveDto));

        verify(accAccountValidationService).controlAreFieldsNotNull(accAccount);
    }

    @Test
    void shouldNotSaveAccount_WhenFields_AreNull(){

        AccAccountSaveDto accAccountSaveDto = mock(AccAccountSaveDto.class);
        AccAccount accAccount = mock(AccAccount.class);

        doThrow(IllegalFieldException.class).when(accAccountValidationService)
                .controlAreFieldsNotNull(accAccount);

        assertThrows(IllegalFieldException.class, () -> accAccountService.saveAccount(accAccountSaveDto));

        verify(accAccountValidationService).controlAreFieldsNotNull(accAccount);
    }

    @Test
    void shouldNotSaveAccount_WhenBalance_IsNotNegative(){

        AccAccountSaveDto accAccountSaveDto = mock(AccAccountSaveDto.class);
        AccAccount accAccount = mock(AccAccount.class);

        when(accAccount.getCurrentBalance()).thenReturn(BigDecimal.valueOf(-1));
        when(accAccountSaveDto.getCurrentBalance()).thenReturn(BigDecimal.valueOf(-1));

        doThrow(IllegalFieldException.class).when(accAccountValidationService).controlIsBalanceNotNegative(accAccount);

        assertThrows(IllegalFieldException.class, () -> accAccountService.saveAccount(accAccountSaveDto));

        verify(accAccountValidationService).controlIsBalanceNotNegative(accAccount);
    }

    @Test
    void shouldCancelAccount() {

        AccAccount accAccount = mock(AccAccount.class);

        when(accAccountEntityService.getByIdWithControl(anyLong())).thenReturn(accAccount);

        accAccountService.cancelAccount(anyLong());

        verify(accAccountEntityService).getByIdWithControl(anyLong());
        verify(accAccountEntityService).save(any());
    }

    @Test
    void shouldNotCancelAccount_WhenId_DoesNotExist(){

        when(accAccountEntityService.getByIdWithControl(anyLong())).thenThrow(ItemNotFoundException.class);

        assertThrows(ItemNotFoundException.class, () -> accAccountService.cancelAccount(anyLong()));

        verify(accAccountEntityService).getByIdWithControl(anyLong());
    }
}