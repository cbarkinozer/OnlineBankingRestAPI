package com.cbarkinozer.onlinebankingrestapi.app.acc.service;

import com.cbarkinozer.onlinebankingrestapi.app.acc.service.entityservice.AccAccountActivityEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.entityservice.AccAccountEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

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
    void moneyOut() {
    }

    @Test
    void moneyIn() {
    }

    @Test
    void withdraw() {
    }

    @Test
    void deposit() {
    }
}