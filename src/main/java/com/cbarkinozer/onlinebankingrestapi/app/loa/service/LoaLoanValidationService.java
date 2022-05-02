package com.cbarkinozer.onlinebankingrestapi.app.loa.service;

import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.IllegalFieldException;
import com.cbarkinozer.onlinebankingrestapi.app.loa.dto.LoaCalculateLoanDto;
import com.cbarkinozer.onlinebankingrestapi.app.loa.enums.LoaErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class LoaLoanValidationService {

    public void controlIsParameterNotNull(LoaCalculateLoanDto loaCalculateLoanDto) {

        boolean hasNull = loaCalculateLoanDto.getInstallmentCount() == null ||
                          loaCalculateLoanDto.getPrincipalLoanAmount() == null;

        if(hasNull){
            throw new IllegalFieldException(LoaErrorMessage.PARAMETER_CANNOT_BE_NULL);
        }
    }


    public void controlIsInterestRateNotNegative(BigDecimal interest_rate) {

        if(interest_rate.compareTo(BigDecimal.ZERO)>=0){
            throw new IllegalFieldException(LoaErrorMessage.INTEREST_RATE_CANNOT_BE_NEGATIVE);
        }
    }


    public void controlIsInstallmentAmountPositive(BigDecimal monthlyInstallmentAmount) {

        if(monthlyInstallmentAmount.compareTo(BigDecimal.ZERO)>0){
            throw new IllegalFieldException(LoaErrorMessage.INSTALLMENT_AMOUNT_MUST_BE_POSITIVE);
        }
    }

    public void controlIsTotalLoanPaymentPositive(BigDecimal totalLoanPayment) {

        if(totalLoanPayment.compareTo(BigDecimal.ZERO)>0){
            throw new IllegalFieldException(LoaErrorMessage.TOTAL_LOAN_PAYMENT_MUST_BE_POSITIVE);
        }
    }
}
