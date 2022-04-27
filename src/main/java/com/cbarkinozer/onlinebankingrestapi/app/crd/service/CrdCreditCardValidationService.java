package com.cbarkinozer.onlinebankingrestapi.app.crd.service;

import com.cbarkinozer.onlinebankingrestapi.app.crd.enums.CrdErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.crd.service.entityservice.CrdCreditCardEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.IllegalFieldException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class CrdCreditCardValidationService {

    private final CrdCreditCardEntityService crdCreditCardEntityService;


    public void isCutOffDayValid(Integer cutOffDay) {
        if(cutOffDay < 1 || cutOffDay > 31 ){
            throw new IllegalFieldException(CrdErrorMessage.CUT_OFF_DAY_IS_NOT_VALID);
        }
    }
}
