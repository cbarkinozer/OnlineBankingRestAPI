package com.cbarkinozer.onlinebankingrestapi.app.cus.service;

import com.cbarkinozer.onlinebankingrestapi.app.cus.dto.CusCustomerDto;
import com.cbarkinozer.onlinebankingrestapi.app.cus.entity.CusCustomer;
import com.cbarkinozer.onlinebankingrestapi.app.cus.service.entityservice.CusCustomerEntityService;
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
    void shouldSaveCustomer() {


    }

    @Test
    void shouldUpdateCustomer() {
    }

    @Test
    void shouldDeleteCustomer() {

        CusCustomer cusCustomer = createDummyCusCustomer();
        doNothing().when(cusCustomerEntityService).delete(cusCustomer);
        cusCustomerService.deleteCustomer(anyLong());
        verify(cusCustomerEntityService).delete(cusCustomer);
    }
}