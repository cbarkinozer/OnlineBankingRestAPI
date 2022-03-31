package com.cbarkinozer.onlinebankingrestapi.app.cus.service;

import com.cbarkinozer.onlinebankingrestapi.app.cus.service.entityservice.CusCustomerEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CusCustomerValidationService {

    private final CusCustomerEntityService cusCustomerEntityService;
}
