package com.cbarkinozer.onlinebankingrestapi.app.cus.service.entityservice;

import com.cbarkinozer.onlinebankingrestapi.app.cus.dao.CusCustomerDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CusCustomerEntityServiceTest {

    @Mock
    CusCustomerDao cusCustomerDao;

    @InjectMocks
    private CusCustomerEntityService cusCustomerEntityService;

    @Test
    void findAllCustomers() {
    }

    @Test
    void saveCustomer() {
    }

    @Test
    void findByIdentityNo() {
    }
}