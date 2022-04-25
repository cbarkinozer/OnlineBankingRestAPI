package com.cbarkinozer.onlinebankingrestapi.app.acc.controller;

import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.*;
import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.*;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.AccAccountActivityService;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.AccAccountService;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.AccMoneyTransferService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.dto.RestResponse;
import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.GenErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.GenStatusType;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.GenBusinessException;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.IllegalFieldException;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.ItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccAccountControllerTest {

    @Mock
    private AccAccountService accAccountService;

    @Mock
    private AccMoneyTransferService accMoneyTransferService;

    @Mock
    private AccAccountActivityService accAccountActivityService;

    @InjectMocks
    private AccAccountController accAccountController;

    @Test
    void shouldFindAllAccounts() {

        List<AccAccountDto> dummyAccAccountDtoList = createDummyAccAccountDtoList();

        when(accAccountService.findAllAccounts()).thenReturn(dummyAccAccountDtoList);

        ResponseEntity<RestResponse<List<AccAccountDto>>> result = accAccountController.findAllAccounts();

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),dummyAccAccountDtoList);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotFindAllAccounts_WhenAccAccount_DoesNotExist(){

        ItemNotFoundException itemNotFoundException = new ItemNotFoundException(GenErrorMessage.ITEM_NOT_FOUND);

        when(accAccountService.findAllAccounts()).thenThrow(itemNotFoundException);

        ItemNotFoundException result = assertThrows(ItemNotFoundException.class, () -> accAccountController.findAllAccounts());

        assertEquals(itemNotFoundException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(itemNotFoundException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    private AccAccountDto createDummyAccAccountDto(){

        AccAccountDto accAccountDto = mock(AccAccountDto.class);

        accAccountDto.setId(1L);
        accAccountDto.setCustomerId(1L);
        accAccountDto.setIbanNo("11111111");
        accAccountDto.setCurrentBalance(BigDecimal.valueOf(100));
        accAccountDto.setCurrencyType(AccCurrencyType.TL);
        accAccountDto.setAccountType(AccAccountType.DEPOSIT);
        accAccountDto.setStatusType(GenStatusType.ACTIVE);
        return  accAccountDto;
    }

    private List<AccAccountDto> createDummyAccAccountDtoList(){

        List<AccAccountDto>  accAccountDtoList = new ArrayList<>();

        AccAccountDto dummyAccAccountDto = createDummyAccAccountDto();
        accAccountDtoList.add(dummyAccAccountDto);

        return accAccountDtoList;
    }

    @Test
    void shouldFindAccountById() {
        AccAccountDto dummyAccAccountDto = createDummyAccAccountDto();

        when(accAccountService.findAccountById(1L)).thenReturn(dummyAccAccountDto);

        ResponseEntity<RestResponse<AccAccountDto>> result = accAccountController.findAccountById(1L);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),dummyAccAccountDto);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldFindAccountById_WhenAccAccount_DoesNotExistById(){

        ItemNotFoundException itemNotFoundException = new ItemNotFoundException(GenErrorMessage.ITEM_NOT_FOUND);

        when(accAccountService.findAccountById(anyLong())).thenThrow(itemNotFoundException);

        ItemNotFoundException result = assertThrows(ItemNotFoundException.class, () -> accAccountController.findAccountById(anyLong()));

        assertEquals(itemNotFoundException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(itemNotFoundException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldFindAccountByCustomerId() {

        List<AccAccountDto> dummyAccAccountDtoList = createDummyAccAccountDtoList();

        when(accAccountService.findAccountByCustomerId(1L)).thenReturn(dummyAccAccountDtoList);

        ResponseEntity<RestResponse<List<AccAccountDto>>> result = accAccountController.findAccountByCustomerId(1L);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),dummyAccAccountDtoList);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotFindAccountByCustomerId_WhenCusCustomer_DoesNotExistById(){

        ItemNotFoundException itemNotFoundException = new ItemNotFoundException(GenErrorMessage.ITEM_NOT_FOUND);

        when(accAccountService.findAccountById(anyLong())).thenThrow(itemNotFoundException);

        ItemNotFoundException result = assertThrows(ItemNotFoundException.class, () -> accAccountController.findAccountById(anyLong()));

        assertEquals(itemNotFoundException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(itemNotFoundException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldSaveAccount() {

        AccAccountSaveDto dummyAccAccountSaveDto = createDummyAccAccountSaveDto();
        AccAccountDto dummyAccAccountDto = createDummyAccAccountDto();

        when(accAccountService.saveAccount(dummyAccAccountSaveDto)).thenReturn(dummyAccAccountDto);

        ResponseEntity<RestResponse<MappingJacksonValue>> result = accAccountController.saveAccount(dummyAccAccountSaveDto);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertNotNull(result.getBody().getData());
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotSaveAccount_WhenFields_AreNull(){

        IllegalFieldException illegalFieldException = new IllegalFieldException(AccErrorMessage.FIELD_CANNOT_BE_NULL);
        AccAccountSaveDto accAccountSaveDto = createDummyAccAccountSaveDto();

        when(accAccountService.saveAccount(accAccountSaveDto)).thenThrow(illegalFieldException);

        IllegalFieldException result = assertThrows(IllegalFieldException.class, () -> accAccountController.saveAccount(accAccountSaveDto));

        assertEquals(illegalFieldException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(illegalFieldException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotSaveAccount_WhenIbanNo_IsNotUnique(){

        IllegalFieldException illegalFieldException = new IllegalFieldException(AccErrorMessage.IBAN_NO_IS_NOT_UNIQUE);
        AccAccountSaveDto accAccountSaveDto = createDummyAccAccountSaveDto();

        when(accAccountService.saveAccount(accAccountSaveDto)).thenThrow(illegalFieldException);

        IllegalFieldException result = assertThrows(IllegalFieldException.class, () -> accAccountController.saveAccount(accAccountSaveDto));

        assertEquals(illegalFieldException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(illegalFieldException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    private AccAccountSaveDto createDummyAccAccountSaveDto(){

        AccAccountSaveDto accAccountSaveDto = mock(AccAccountSaveDto.class);

        accAccountSaveDto.setCurrentBalance(BigDecimal.valueOf(100));
        accAccountSaveDto.setCurrencyType(AccCurrencyType.TL);
        accAccountSaveDto.setAccountType(AccAccountType.DEPOSIT);

        return  accAccountSaveDto;
    }

    @Test
    void shouldCancelAccount() {

        doNothing().when(accAccountService).cancelAccount(anyLong());
        ResponseEntity<RestResponse<?>> result = accAccountController.cancelAccount(1L);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertNull(result.getBody().getData());
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotCancelAccount_WhenAccAccount_DoesNotExistById(){

        ItemNotFoundException itemNotFoundException = new ItemNotFoundException(AccErrorMessage.ACCOUNT_NOT_FOUND);

        doThrow(itemNotFoundException).when(accAccountService).cancelAccount(1L);

        ItemNotFoundException result = assertThrows(ItemNotFoundException.class, () -> accAccountController.cancelAccount(1L));

        assertEquals(itemNotFoundException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(itemNotFoundException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldTransferMoney() {

        AccMoneyTransferSaveDto dummyAccMoneyTransferSaveDto = createDummyAccMoneyTransferSaveDto();
        AccMoneyTransferDto dummyAccMoneyTransferDto = createDummyAccMoneyTransferDto();

        when(accMoneyTransferService.transferMoney(dummyAccMoneyTransferSaveDto)).thenReturn(dummyAccMoneyTransferDto);

        ResponseEntity<RestResponse<AccMoneyTransferDto>> result = accAccountController.transferMoney(dummyAccMoneyTransferSaveDto);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertNotNull(result.getBody().getData());
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotTransferMoney_WhenAccMoneyTransferSaveDto_IsNull() {

        GenBusinessException genBusinessException = new GenBusinessException(GenErrorMessage.PARAMETER_CANNOT_BE_NULL);

        AccMoneyTransferSaveDto dummyAccMoneyTransferSaveDto = createDummyAccMoneyTransferSaveDto();

        doThrow(genBusinessException).when(accMoneyTransferService).transferMoney(dummyAccMoneyTransferSaveDto);

        GenBusinessException result = assertThrows(GenBusinessException.class, () -> accAccountController.transferMoney(dummyAccMoneyTransferSaveDto));

        assertEquals(genBusinessException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(genBusinessException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);

    }

    @Test
    void shouldNotTransferMoney_WhenAmount_IsNotPositive() {

        GenBusinessException genBusinessException = new GenBusinessException(AccErrorMessage.AMOUNT_MUST_BE_POSITIVE);

        AccMoneyTransferSaveDto dummyAccMoneyTransferSaveDto = createDummyAccMoneyTransferSaveDto();

        doThrow(genBusinessException).when(accMoneyTransferService).transferMoney(dummyAccMoneyTransferSaveDto);

        GenBusinessException result = assertThrows(GenBusinessException.class, () -> accAccountController.transferMoney(dummyAccMoneyTransferSaveDto));

        assertEquals(genBusinessException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(genBusinessException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);

    }

    @Test
    void shouldNotTransferMoney_WhenAccountIds_DoesNotExist() {

        IllegalFieldException illegalFieldException = new IllegalFieldException(AccErrorMessage.ACCOUNT_NOT_FOUND);

        AccMoneyTransferSaveDto dummyAccMoneyTransferSaveDto = createDummyAccMoneyTransferSaveDto();

        doThrow(illegalFieldException).when(accMoneyTransferService).transferMoney(dummyAccMoneyTransferSaveDto);

        GenBusinessException result = assertThrows(IllegalFieldException.class, () -> accAccountController.transferMoney(dummyAccMoneyTransferSaveDto));

        assertEquals(illegalFieldException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(illegalFieldException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);

    }

    private AccMoneyTransferSaveDto createDummyAccMoneyTransferSaveDto(){

        AccMoneyTransferSaveDto accMoneyTransferSaveDto = mock(AccMoneyTransferSaveDto.class);

        accMoneyTransferSaveDto.setTransferType(AccMoneyTransferType.DUE);
        accMoneyTransferSaveDto.setAccountIdTo(1L);
        accMoneyTransferSaveDto.setAccountIdFrom(2L);
        accMoneyTransferSaveDto.setAmount(BigDecimal.valueOf(100));
        accMoneyTransferSaveDto.setDescription("Here some test bucks.");

        return  accMoneyTransferSaveDto;
    }

    private AccMoneyTransferDto createDummyAccMoneyTransferDto(){

        AccMoneyTransferDto accMoneyTransferDto = mock(AccMoneyTransferDto.class);

        accMoneyTransferDto.setAccountIdFrom(2L);
        accMoneyTransferDto.setAccountIdTo(1L);
        accMoneyTransferDto.setAmount(BigDecimal.valueOf(100));
        accMoneyTransferDto.setTransferDate(LocalDate.now());
        accMoneyTransferDto.setDescription("Here some test money.");
        accMoneyTransferDto.setTransferType(AccMoneyTransferType.RENTAL);

        return  accMoneyTransferDto;
    }

    @Test
    void shouldWithdraw() {

        AccMoneyActivityRequestDto dummyAccMoneyActivityRequestDto = createDummyAccMoneyActivityRequestDto();
        AccAccountActivityDto dummyAccAccountActivityDto = createDummyAccAccountActivityDto();

        when(accAccountActivityService.withdraw(dummyAccMoneyActivityRequestDto)).thenReturn(dummyAccAccountActivityDto);

        ResponseEntity<RestResponse<AccAccountActivityDto>> result = accAccountController.withdraw(dummyAccMoneyActivityRequestDto);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertNotNull(result.getBody().getData());
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotWithdraw_WhenAccMoneyActivityRequestDto_IsNull() {

        GenBusinessException genBusinessException = new GenBusinessException(GenErrorMessage.PARAMETER_CANNOT_BE_NULL);

        AccMoneyActivityRequestDto dummyAccMoneyActivityRequestDto = createDummyAccMoneyActivityRequestDto();

        doThrow(genBusinessException).when(accAccountActivityService).withdraw(dummyAccMoneyActivityRequestDto);

        GenBusinessException result = assertThrows(GenBusinessException.class, () -> accAccountController.withdraw(dummyAccMoneyActivityRequestDto));

        assertEquals(genBusinessException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(genBusinessException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);

    }

    @Test
    void shouldNotWithdraw_WhenAccountId_DoesNotExist() {

        GenBusinessException genBusinessException = new GenBusinessException(AccErrorMessage.ACCOUNT_NOT_FOUND);

        AccMoneyActivityRequestDto dummyAccMoneyActivityRequestDto = createDummyAccMoneyActivityRequestDto();

        doThrow(genBusinessException).when(accAccountActivityService).withdraw(dummyAccMoneyActivityRequestDto);

        GenBusinessException result = assertThrows(GenBusinessException.class, () -> accAccountController.withdraw(dummyAccMoneyActivityRequestDto));

        assertEquals(genBusinessException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(genBusinessException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotWithdraw_WhenAmount_IsNotPositive() {

        GenBusinessException genBusinessException = new GenBusinessException(AccErrorMessage.AMOUNT_MUST_BE_POSITIVE);

        AccMoneyActivityRequestDto dummyAccMoneyActivityRequestDto = createDummyAccMoneyActivityRequestDto();

        doThrow(genBusinessException).when(accAccountActivityService).withdraw(dummyAccMoneyActivityRequestDto);

        GenBusinessException result = assertThrows(GenBusinessException.class, () -> accAccountController.withdraw(dummyAccMoneyActivityRequestDto));

        assertEquals(genBusinessException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(genBusinessException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    private AccMoneyActivityRequestDto createDummyAccMoneyActivityRequestDto(){

        AccMoneyActivityRequestDto accMoneyActivityRequestDto = mock(AccMoneyActivityRequestDto.class);

        accMoneyActivityRequestDto.setAccountId(1L);
        accMoneyActivityRequestDto.setAmount(BigDecimal.valueOf(100));

        return  accMoneyActivityRequestDto;
    }

    private AccAccountActivityDto createDummyAccAccountActivityDto(){

        AccAccountActivityDto accAccountActivityDto = mock(AccAccountActivityDto.class);

        accAccountActivityDto.setAccountId(1L);
        accAccountActivityDto.setAmount(BigDecimal.valueOf(100));
        accAccountActivityDto.setTransactionDate(LocalDate.now());
        accAccountActivityDto.setCurrentBalance(BigDecimal.valueOf(100));
        accAccountActivityDto.setAccountActivityType(AccAccountActivityType.WITHDRAW);

        return  accAccountActivityDto;
    }

    @Test
    void shouldDeposit() {

        AccMoneyActivityRequestDto dummyAccMoneyActivityRequestDto = createDummyAccMoneyActivityRequestDto();
        AccAccountActivityDto dummyAccAccountActivityDto = createDummyAccAccountActivityDto();

        dummyAccAccountActivityDto.setAccountActivityType(AccAccountActivityType.DEPOSIT);

        when(accAccountActivityService.deposit(dummyAccMoneyActivityRequestDto)).thenReturn(dummyAccAccountActivityDto);

        ResponseEntity<RestResponse<AccAccountActivityDto>> result = accAccountController.deposit(dummyAccMoneyActivityRequestDto);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertNotNull(result.getBody().getData());
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotDeposit_WhenAccMoneyActivityRequestDto_IsNull() {

        GenBusinessException genBusinessException = new GenBusinessException(GenErrorMessage.PARAMETER_CANNOT_BE_NULL);

        AccMoneyActivityRequestDto dummyAccMoneyActivityRequestDto = createDummyAccMoneyActivityRequestDto();

        doThrow(genBusinessException).when(accAccountActivityService).deposit(dummyAccMoneyActivityRequestDto);

        GenBusinessException result = assertThrows(GenBusinessException.class, () -> accAccountController.deposit(dummyAccMoneyActivityRequestDto));

        assertEquals(genBusinessException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(genBusinessException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);

    }

    @Test
    void shouldNotDeposit_WhenAccountId_DoesNotExist() {

        GenBusinessException genBusinessException = new GenBusinessException(AccErrorMessage.ACCOUNT_NOT_FOUND);

        AccMoneyActivityRequestDto dummyAccMoneyActivityRequestDto = createDummyAccMoneyActivityRequestDto();

        doThrow(genBusinessException).when(accAccountActivityService).deposit(dummyAccMoneyActivityRequestDto);

        GenBusinessException result = assertThrows(GenBusinessException.class, () -> accAccountController.deposit(dummyAccMoneyActivityRequestDto));

        assertEquals(genBusinessException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(genBusinessException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotDeposit_WhenAmount_IsNotPositive() {

        GenBusinessException genBusinessException = new GenBusinessException(AccErrorMessage.AMOUNT_MUST_BE_POSITIVE);

        AccMoneyActivityRequestDto dummyAccMoneyActivityRequestDto = createDummyAccMoneyActivityRequestDto();

        doThrow(genBusinessException).when(accAccountActivityService).deposit(dummyAccMoneyActivityRequestDto);

        GenBusinessException result = assertThrows(GenBusinessException.class, () -> accAccountController.deposit(dummyAccMoneyActivityRequestDto));

        assertEquals(genBusinessException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(genBusinessException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }
}