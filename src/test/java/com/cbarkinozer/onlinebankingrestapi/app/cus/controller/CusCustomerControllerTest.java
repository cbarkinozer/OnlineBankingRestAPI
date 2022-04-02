package com.cbarkinozer.onlinebankingrestapi.app.cus.controller;

import com.cbarkinozer.onlinebankingrestapi.app.cus.dto.CusCustomerDto;
import com.cbarkinozer.onlinebankingrestapi.app.cus.dto.CusCustomerSaveDto;
import com.cbarkinozer.onlinebankingrestapi.app.cus.dto.CusCustomerUpdateDto;
import com.cbarkinozer.onlinebankingrestapi.app.cus.enums.CusErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.cus.service.CusCustomerService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.BaseTest;
import com.cbarkinozer.onlinebankingrestapi.app.gen.dto.RestResponse;
import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.GenErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.IllegalFieldException;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.ItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CusCustomerControllerTest{

    @Mock
    private CusCustomerService cusCustomerService;

    @InjectMocks
    private CusCustomerController cusCustomerController;

    @Test
    void shouldFindAllCustomers() {

        List<CusCustomerDto> dummyCusCustomerDtoList = createDummyCusCustomerDtoList();

        when(cusCustomerService.findAllCustomers()).thenReturn(dummyCusCustomerDtoList);

        ResponseEntity<RestResponse<List<CusCustomerDto>>> result = cusCustomerController.findAllCustomers();

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),dummyCusCustomerDtoList);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotFindAllCustomers_WhenCusCustomer_DoesNotExist(){

        ItemNotFoundException itemNotFoundException = new ItemNotFoundException(GenErrorMessage.ITEM_NOT_FOUND);

        when(cusCustomerService.findAllCustomers()).thenThrow(itemNotFoundException);

        ItemNotFoundException result = assertThrows(ItemNotFoundException.class, () -> cusCustomerController.findAllCustomers());

        assertEquals(itemNotFoundException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(itemNotFoundException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    private CusCustomerDto createDummyCusCustomerDto(){

        CusCustomerDto cusCustomerDto = mock(CusCustomerDto.class);

        cusCustomerDto.setName("testName");
        cusCustomerDto.setSurname("testSurname");
        cusCustomerDto.setIdentityNo(1L);

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

        CusCustomerDto dummyCusCustomerDto = createDummyCusCustomerDto();

        when(cusCustomerService.findCustomerById(1L)).thenReturn(dummyCusCustomerDto);

        ResponseEntity<RestResponse<CusCustomerDto>> result = cusCustomerController.findCustomerById(1L);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),dummyCusCustomerDto);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotFindCustomerById_WhenCusCustomer_DoesNotExistById(){

        ItemNotFoundException itemNotFoundException = new ItemNotFoundException(GenErrorMessage.ITEM_NOT_FOUND);

        when(cusCustomerService.findCustomerById(anyLong())).thenThrow(itemNotFoundException);

        ItemNotFoundException result = assertThrows(ItemNotFoundException.class, () -> cusCustomerController.findCustomerById(anyLong()));

        assertEquals(itemNotFoundException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(itemNotFoundException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldSaveCustomer() {

        CusCustomerSaveDto dummyCusCustomerSaveDto = createDummyCusCustomerSaveDto();
        CusCustomerDto dummyCusCustomerDto = createDummyCusCustomerDto();

        when(cusCustomerService.saveCustomer(dummyCusCustomerSaveDto)).thenReturn(dummyCusCustomerDto);

        ResponseEntity<RestResponse<MappingJacksonValue>> result = cusCustomerController.saveCustomer(dummyCusCustomerSaveDto);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertNotNull(result.getBody().getData());
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotSaveCustomer_WhenFields_AreNull(){

        IllegalFieldException illegalFieldException = new IllegalFieldException(CusErrorMessage.FIELD_CANNOT_BE_NULL);
        CusCustomerSaveDto cusCustomerSaveDto = createDummyCusCustomerSaveDto();

        when(cusCustomerService.saveCustomer(cusCustomerSaveDto)).thenThrow(illegalFieldException);

        IllegalFieldException result = assertThrows(IllegalFieldException.class, () -> cusCustomerController.saveCustomer(cusCustomerSaveDto));

        assertEquals(illegalFieldException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(illegalFieldException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotSaveCustomer_WhenIdentityNo_IsNotUnique(){

        IllegalFieldException illegalFieldException = new IllegalFieldException(CusErrorMessage.IDENTITY_NO_MUST_BE_UNIQUE);
        CusCustomerSaveDto cusCustomerSaveDto = createDummyCusCustomerSaveDto();

        when(cusCustomerService.saveCustomer(cusCustomerSaveDto)).thenThrow(illegalFieldException);

        IllegalFieldException result = assertThrows(IllegalFieldException.class, () -> cusCustomerController.saveCustomer(cusCustomerSaveDto));

        assertEquals(illegalFieldException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(illegalFieldException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    private CusCustomerSaveDto createDummyCusCustomerSaveDto(){

        CusCustomerSaveDto cusCustomerSaveDto = mock(CusCustomerSaveDto.class);

        cusCustomerSaveDto.setName("testName");
        cusCustomerSaveDto.setSurname("testSurname");
        cusCustomerSaveDto.setIdentityNo(1L);
        cusCustomerSaveDto.setPassword("test1234");

        return  cusCustomerSaveDto;
    }

    @Test
    void shouldUpdateCustomer() {

        CusCustomerDto dummyCusCustomerDto = createDummyCusCustomerDto();
        CusCustomerUpdateDto dummyCusCustomerUpdateDto = createDummyCusCustomerUpdateDto();

        when(cusCustomerService.updateCustomer(dummyCusCustomerUpdateDto)).thenReturn(dummyCusCustomerDto);

        ResponseEntity<RestResponse<CusCustomerDto>> result = cusCustomerController.updateCustomer(dummyCusCustomerUpdateDto);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),dummyCusCustomerDto);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    private CusCustomerUpdateDto createDummyCusCustomerUpdateDto(){

        CusCustomerUpdateDto cusCustomerUpdateDto = mock(CusCustomerUpdateDto.class);

        cusCustomerUpdateDto.setId(1L);
        cusCustomerUpdateDto.setName("testName");
        cusCustomerUpdateDto.setSurname("testSurname");
        cusCustomerUpdateDto.setIdentityNo(1L);
        cusCustomerUpdateDto.setPassword("test1234");

        return  cusCustomerUpdateDto;
    }

    @Test
    void shouldNotUpdateCustomer_WhenFields_AreNull(){

        IllegalFieldException illegalFieldException = new IllegalFieldException(CusErrorMessage.FIELD_CANNOT_BE_NULL);
        CusCustomerUpdateDto cusCustomerUpdateDto = createDummyCusCustomerUpdateDto();

        when(cusCustomerService.updateCustomer(cusCustomerUpdateDto)).thenThrow(illegalFieldException);

        IllegalFieldException result = assertThrows(IllegalFieldException.class,
                () -> cusCustomerController.updateCustomer(cusCustomerUpdateDto));

        assertEquals(illegalFieldException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(illegalFieldException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotUpdateCustomer_WhenIdentityNo_IsNotUnique(){

        IllegalFieldException illegalFieldException = new IllegalFieldException(CusErrorMessage.IDENTITY_NO_MUST_BE_UNIQUE);
        CusCustomerUpdateDto cusCustomerUpdateDto = createDummyCusCustomerUpdateDto();

        when(cusCustomerService.updateCustomer(cusCustomerUpdateDto)).thenThrow(illegalFieldException);

        IllegalFieldException result = assertThrows(IllegalFieldException.class,
                () -> cusCustomerController.updateCustomer(cusCustomerUpdateDto));

        assertEquals(illegalFieldException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(illegalFieldException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }

    @Test
    void shouldDeleteCustomer() {

        doNothing().when(cusCustomerService).deleteCustomer(anyLong());
        ResponseEntity<RestResponse<?>> result = cusCustomerController.deleteCustomer(1L);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertNull(result.getBody().getData());
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldNotDeleteCustomer_WhenCusCustomer_DoesNotExistById(){

        ItemNotFoundException itemNotFoundException = new ItemNotFoundException(CusErrorMessage.IDENTITY_NO_MUST_BE_UNIQUE);

        doThrow(itemNotFoundException).when(cusCustomerService).deleteCustomer(1L);

        ItemNotFoundException result = assertThrows(ItemNotFoundException.class, () -> cusCustomerController.deleteCustomer(1L));

        assertEquals(itemNotFoundException.getBaseErrorMessage().getMessage(), result.getBaseErrorMessage().getMessage());
        assertEquals(itemNotFoundException.getBaseErrorMessage().getDetailMessage(), result.getBaseErrorMessage().getDetailMessage());
        assertNotNull(result);
    }
}