package com.cbarkinozer.onlinebankingrestapi.app.crd.service;

import com.cbarkinozer.onlinebankingrestapi.app.crd.dto.CrdCreditCardSpendDto;
import com.cbarkinozer.onlinebankingrestapi.app.crd.entity.CrdCreditCard;
import com.cbarkinozer.onlinebankingrestapi.app.crd.enums.CrdErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.crd.service.entityservice.CrdCreditCardEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.GenStatusType;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.GenBusinessException;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.IllegalFieldException;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    public void controlIsEarningNotNegative(BigDecimal earning) {

        if(earning.compareTo(BigDecimal.ZERO)<0){
            throw new IllegalFieldException(CrdErrorMessage.EARNING_CANNOT_BE_NEGATIVE);
        }
    }

    public void controlAreFieldsNull(BigDecimal earning, Integer cutOffDay) {

        if(earning == null || cutOffDay == null){
            throw new IllegalFieldException(CrdErrorMessage.FIELDS_CANNOT_BE_NEGATIVE);
        }
    }

    public void controlAreFieldsNull(Long creditCardId, BigDecimal amount) {

        if(creditCardId == null || amount == null){
            throw new IllegalFieldException(CrdErrorMessage.FIELDS_CANNOT_BE_NEGATIVE);
        }
    }

    public void controlAreFieldsNull(CrdCreditCardSpendDto crdCreditCardSpendDto) {

        Long cardNo = crdCreditCardSpendDto.getCardNo();
        Long cvvNo = crdCreditCardSpendDto.getCvvNo();
        LocalDate expireDate = crdCreditCardSpendDto.getExpireDate();
        BigDecimal amount = crdCreditCardSpendDto.getAmount();
        String description = crdCreditCardSpendDto.getDescription();

        boolean hasNull =
                        cardNo == null      ||
                        cvvNo == null       ||
                        expireDate == null  ||
                        amount == null      ||
                        description.isBlank();

        if(hasNull){
            throw new IllegalFieldException(CrdErrorMessage.FIELDS_CANNOT_BE_NEGATIVE);
        }
    }

    public void controlIsCardCancelled(GenStatusType genStatusType) {

        if(genStatusType == GenStatusType.PASSIVE){

            throw new IllegalFieldException(CrdErrorMessage.CREDIT_CARD_CANCELLED);
        }
    }
}
