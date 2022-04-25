package com.cbarkinozer.onlinebankingrestapi.app.acc.service;

import com.cbarkinozer.onlinebankingrestapi.app.acc.service.entityservice.AccMoneyTransferEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    }
}