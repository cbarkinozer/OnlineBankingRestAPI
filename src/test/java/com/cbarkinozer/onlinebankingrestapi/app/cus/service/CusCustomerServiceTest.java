package com.cbarkinozer.onlinebankingrestapi.app.cus.service;

import com.cbarkinozer.onlinebankingrestapi.app.cus.dto.CusCustomerDto;
import com.cbarkinozer.onlinebankingrestapi.app.cus.dto.CusCustomerSaveDto;
import com.cbarkinozer.onlinebankingrestapi.app.cus.dto.CusCustomerUpdateDto;
import com.cbarkinozer.onlinebankingrestapi.app.cus.entity.CusCustomer;
import com.cbarkinozer.onlinebankingrestapi.app.cus.enums.CusErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.cus.service.entityservice.CusCustomerEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.GenErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.IllegalFieldException;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.ItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CusCustomerServiceTest {

    @Mock
    private CusCustomerEntityService cusCustomerEntityService;

    @Mock
    private CusCustomerValidationService cusCustomerValidationService;

    @InjectMocks
    private CusCustomerService cusCustomerService;

    @Test
    void shouldFindAllCustomers() {

        List<CusCustomer> cusCustomerList = createDummyCusCustomerList();

        List<CusCustomerDto> expectedResult = createDummyCusCustomerDtoList();

        when(cusCustomerEntityService.findAllCustomers()).thenReturn(cusCustomerList);

        List<CusCustomerDto> result = cusCustomerService.findAllCustomers();

        assertEquals(expectedResult, result);
        assertNotNull(result);
    }

    private CusCustomer createDummyCusCustomer(){

        CusCustomer cusCustomer = new CusCustomer();
        cusCustomer.setId(1L);
        cusCustomer.setName("testName");
        cusCustomer.setSurname("testSurname");
        cusCustomer.setIdentityNo(11111111111L);
        cusCustomer.setPassword("testPassword");
        return  cusCustomer;
    }

    private List<CusCustomer> createDummyCusCustomerList(){

        List<CusCustomer>  cusCustomerList = new ArrayList<>();

        CusCustomer dummyCusCustomer = createDummyCusCustomer();
        cusCustomerList.add(dummyCusCustomer);

        return cusCustomerList;
    }

    private CusCustomerDto createDummyCusCustomerDto(){

        CusCustomerDto cusCustomerDto = new CusCustomerDto();
        cusCustomerDto.setId(1L);
        cusCustomerDto.setName("testName");
        cusCustomerDto.setSurname("testSurname");
        cusCustomerDto.setIdentityNo(11111111111L);

        return  cusCustomerDto;
    }
    private List<CusCustomerDto> createDummyCusCustomerDtoList(){

        List<CusCustomerDto>  cusCustomerDtoList = new ArrayList<>();

        CusCustomerDto dummyCusCustomerDto = createDummyCusCustomerDto();
        cusCustomerDtoList.add(dummyCusCustomerDto);

        return cusCustomerDtoList;
    }

    @Test
    void shouldFindCustomerById() {

        CusCustomer cusCustomer = createDummyCusCustomer();
        Long cusCustomerId = cusCustomer.getId();
        CusCustomerDto expectedResult = createDummyCusCustomerDto();

        when(cusCustomerEntityService.getByIdWithControl(cusCustomerId)).thenReturn(cusCustomer);

        CusCustomerDto result = cusCustomerService.findCustomerById(cusCustomerId);

        assertEquals(expectedResult, result);
        assertNotNull(result);
    }

    @Test
    void shouldNotFindCustomerById_WhenCusCustomerId_DoesNotExist() {

        ItemNotFoundException itemNotFoundException = new ItemNotFoundException(GenErrorMessage.ITEM_NOT_FOUND);

        when(cusCustomerEntityService.getByIdWithControl(anyLong())).thenThrow(itemNotFoundException);

        ItemNotFoundException result = assertThrows(ItemNotFoundException.class,
                () -> cusCustomerService.findCustomerById(anyLong()));

        assertEquals(itemNotFoundException, result);
        assertEquals(itemNotFoundException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(itemNotFoundException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldSaveCustomer() {

        CusCustomerDto cusCustomerDto = createDummyCusCustomerDto();
        CusCustomer cusCustomer = createDummyCusCustomer();

        when(cusCustomerEntityService.saveCustomer(any())).thenReturn(cusCustomer);

        CusCustomerDto result = cusCustomerService.saveCustomer(any());

        assertEquals(cusCustomerDto, result);
        assertNotNull(result);
    }

    @Test
    void shouldNotSaveCustomer_WhenIdentityNo_IsNotUnique() {

        CusCustomerSaveDto cusCustomerSaveDto = createDummyCusCustomerSaveDto();
        CusCustomer cusCustomer = createDummyCusCustomer();
        IllegalFieldException illegalFieldException = new IllegalFieldException(CusErrorMessage.IDENTITY_NO_MUST_BE_UNIQUE);

        doThrow(IllegalFieldException.class).when(cusCustomerValidationService).controlIsIdentityNoUnique(cusCustomer);

        IllegalFieldException result = assertThrows(IllegalFieldException.class,
                () -> cusCustomerService.saveCustomer(cusCustomerSaveDto));

        assertEquals(illegalFieldException, result);
        assertEquals(illegalFieldException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(illegalFieldException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotSaveCustomer_WhenFields_AreNull() {

        CusCustomerSaveDto cusCustomerSaveDto = createDummyCusCustomerSaveDto();
        CusCustomer cusCustomer = createDummyCusCustomer();

        IllegalFieldException illegalFieldException = new IllegalFieldException(CusErrorMessage.FIELD_CANNOT_BE_NULL);

        doThrow(IllegalFieldException.class).when(cusCustomerValidationService).controlAreFieldsNonNull(cusCustomer);

        IllegalFieldException result = assertThrows(IllegalFieldException.class,
                () -> cusCustomerService.saveCustomer(cusCustomerSaveDto));

        assertEquals(illegalFieldException, result);
        assertEquals(illegalFieldException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(illegalFieldException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    private CusCustomerSaveDto createDummyCusCustomerSaveDto(){

        CusCustomerSaveDto cusCustomerSaveDto = new CusCustomerSaveDto();

        cusCustomerSaveDto.setName("testName");
        cusCustomerSaveDto.setSurname("testSurname");
        cusCustomerSaveDto.setIdentityNo(11111111111L);
        cusCustomerSaveDto.setPassword("test1234");

        return cusCustomerSaveDto;
    }

    @Test
    void shouldUpdateCustomer() {

        CusCustomerDto cusCustomerDto = createDummyCusCustomerDto();
        CusCustomer cusCustomer = createDummyCusCustomer();

        when(cusCustomerEntityService.saveCustomer(any())).thenReturn(cusCustomer);

        CusCustomerDto result = cusCustomerService.saveCustomer(any());

        assertEquals(cusCustomerDto, result);
        assertNotNull(result);
    }

    @Test
    void shouldNotUpdateCustomer_WhenIdentityNo_IsNotUnique() {

        CusCustomerUpdateDto cusCustomerUpdateDto = createDummyCusCustomerUpdateDto();
        CusCustomer cusCustomer = createDummyCusCustomer();
        IllegalFieldException illegalFieldException = new IllegalFieldException(CusErrorMessage.IDENTITY_NO_MUST_BE_UNIQUE);

        doThrow(IllegalFieldException.class).when(cusCustomerValidationService).controlIsIdentityNoUnique(cusCustomer);

        IllegalFieldException result = assertThrows(IllegalFieldException.class,
                () -> cusCustomerService.updateCustomer(cusCustomerUpdateDto));

        assertEquals(illegalFieldException, result);
        assertEquals(illegalFieldException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(illegalFieldException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    private CusCustomerUpdateDto createDummyCusCustomerUpdateDto(){

        CusCustomerUpdateDto cusCustomerUpdateDto = new CusCustomerUpdateDto();

        cusCustomerUpdateDto.setId(1L);
        cusCustomerUpdateDto.setName("testName");
        cusCustomerUpdateDto.setSurname("testSurname");
        cusCustomerUpdateDto.setIdentityNo(11111111111L);
        cusCustomerUpdateDto.setPassword("test1234");

        return cusCustomerUpdateDto;
    }

    @Test
    void shouldNotUpdateCustomer_WhenFields_AreNull() {

        CusCustomerUpdateDto cusCustomerUpdateDto = createDummyCusCustomerUpdateDto();
        CusCustomer cusCustomer = createDummyCusCustomer();

        IllegalFieldException illegalFieldException = new IllegalFieldException(CusErrorMessage.FIELD_CANNOT_BE_NULL);

        doThrow(IllegalFieldException.class).when(cusCustomerValidationService).controlAreFieldsNonNull(cusCustomer);

        IllegalFieldException result = assertThrows(IllegalFieldException.class,
                () -> cusCustomerService.updateCustomer(cusCustomerUpdateDto));

        assertEquals(illegalFieldException, result);
        assertEquals(illegalFieldException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(illegalFieldException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldDeleteCustomer() {

        CusCustomer cusCustomer = createDummyCusCustomer();

        when(cusCustomerEntityService.getByIdWithControl(anyLong())).thenReturn(cusCustomer);

        cusCustomerService.deleteCustomer(anyLong());

        verify(cusCustomerEntityService).getByIdWithControl(anyLong());
    }

    @Test
    void shouldNotDeleteCustomer_WhenId_DoesNotExist() {

        CusCustomer cusCustomer = createDummyCusCustomer();

        ItemNotFoundException itemNotFoundException = new ItemNotFoundException(GenErrorMessage.ITEM_NOT_FOUND);

        doThrow(new ItemNotFoundException(GenErrorMessage.ITEM_NOT_FOUND)).
                when(cusCustomerEntityService).getByIdWithControl(anyLong());

        ItemNotFoundException result = assertThrows(ItemNotFoundException.class,
                () -> cusCustomerEntityService.delete(cusCustomer));

        verify(cusCustomerEntityService).getByIdWithControl(anyLong());
        assertEquals(itemNotFoundException, result);
        assertEquals(itemNotFoundException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(itemNotFoundException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

}