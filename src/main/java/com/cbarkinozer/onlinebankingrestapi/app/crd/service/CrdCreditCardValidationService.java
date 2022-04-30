package com.cbarkinozer.onlinebankingrestapi.app.crd.service;

import com.cbarkinozer.onlinebankingrestapi.app.crd.entity.CrdCreditCard;
import com.cbarkinozer.onlinebankingrestapi.app.crd.enums.CrdErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.crd.service.entityservice.CrdCreditCardEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.GenBusinessException;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.IllegalFieldException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class CrdCreditCardValidationService {

    private final CrdCreditCardEntityService crdCreditCardEntityService;

    public void validateCardLimit(BigDecimal currentAvailableLimit) {
        if (currentAvailableLimit.compareTo(BigDecimal.ZERO) < 0){
            throw new GenBusinessException(CrdErrorMessage.INSUFFICIENT_CREDIT_CARD_LIMIT);
        }
    }

    public void validateCreditCard(CrdCreditCard crdCreditCard) {
        if (crdCreditCard == null){
            throw new GenBusinessException(CrdErrorMessage.INVALID_CREDIT_CARD);
        }

        if (crdCreditCard.getExpireDate().isBefore(LocalDate.now())){
            throw new GenBusinessException(CrdErrorMessage.CREDIT_CARD_EXPIRED);
        }
    }


    public void isCutOffDayValid(Integer cutOffDay) {
        if(cutOffDay < 1 || cutOffDay > 31 ){
            throw new IllegalFieldException(CrdErrorMessage.CUT_OFF_DAY_IS_NOT_VALID);
        }
    }
}
