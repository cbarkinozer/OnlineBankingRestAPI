package com.cbarkinozer.onlinebankingrestapi.app.loa.service;

import com.cbarkinozer.onlinebankingrestapi.app.cus.service.entityservice.CusCustomerEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.IllegalFieldException;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.ItemNotFoundException;
import com.cbarkinozer.onlinebankingrestapi.app.loa.dto.LoaApplyLoanDto;
import com.cbarkinozer.onlinebankingrestapi.app.loa.dto.LoaCalculateLateFeeDto;
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

    private final CusCustomerEntityService cusCustomerEntityService;

    public void controlIsParameterNotNull(LoaCalculateLoanDto loaCalculateLoanDto) {

        boolean hasNull = loaCalculateLoanDto.getInstallmentCount() == null ||
                          loaCalculateLoanDto.getPrincipalLoanAmount() == null;

        if(hasNull){
            throw new IllegalFieldException(LoaErrorMessage.PARAMETER_CANNOT_BE_NULL);
        }
    }

    public void controlIsParameterNotNull(LoaCalculateLateFeeDto loaCalculateLateFeeDto) {

        boolean hasNull = loaCalculateLateFeeDto.getLateDayCount() == null ||
                loaCalculateLateFeeDto.getTotalLoan() == null;

        if(hasNull){
            throw new IllegalFieldException(LoaErrorMessage.PARAMETER_CANNOT_BE_NULL);
        }
    }

    public void controlIsParameterNotNull(LoaApplyLoanDto loaApplyLoanDto) {

        boolean hasNull = loaApplyLoanDto.getCustomerId() == null ||
                loaApplyLoanDto.getInstallmentCount() == null     ||
                loaApplyLoanDto.getPrincipalLoanAmount() == null  ||
                loaApplyLoanDto.getMonthlySalary() == null;

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

    public void controlIsLateFeeRateNotNegative(BigDecimal lateFeeRate) {

        if(lateFeeRate.compareTo(BigDecimal.ZERO)>=0){
            throw new IllegalFieldException(LoaErrorMessage.LATE_FEE_RATE_CANNOT_BE_NEGATIVE);
        }
    }

    public void controlIsTotalLateFeePositive(BigDecimal totalLateFee) {

        if(totalLateFee.compareTo(BigDecimal.ZERO)>0){
            throw new IllegalFieldException(LoaErrorMessage.TOTAL_LATE_FEE_MUST_BE_POSITIVE);
        }
    }

    public void controlIsLateInterestTaxNotNegative(BigDecimal lateInterestTax) {

        if(lateInterestTax.compareTo(BigDecimal.ZERO)>=0){
            throw new IllegalFieldException(LoaErrorMessage.LATE_INTEREST_TAX_CANNOT_BE_NEGATIVE);
        }
    }

    public void controlIsLoanAmountNotGreaterThanMaxLoanAmount(BigDecimal principalLoanAmount, BigDecimal maxLoanAmount) {

        if(principalLoanAmount.compareTo(maxLoanAmount)>0){

            LoaErrorMessage loaErrorMessage = LoaErrorMessage.LOAN_AMOUNT_CANNOT_BE_GREATER_THAN_MAX_AMOUNT;
            loaErrorMessage.setDetailMessage(String.valueOf(maxLoanAmount));

            throw new IllegalFieldException(loaErrorMessage);
        }
    }

    public void controlIsCustomerExist(Long customerId) {

        boolean isExist = cusCustomerEntityService.existsById(customerId);

        if (!isExist){

            throw new ItemNotFoundException(LoaErrorMessage.CUSTOMER_NOT_FOUND);
        }
    }

    public void controlIsMonthlyInstallmentAmountPositive(BigDecimal monthlyInstallmentAmount) {

        if(monthlyInstallmentAmount.compareTo(BigDecimal.ZERO)>0){
            throw new IllegalFieldException(LoaErrorMessage.MONTHLY_INSTALLMENT_AMOUNT_MUST_BE_POSITIVE);
        }
    }

    public void controlIsInterestAmountNotNegative(BigDecimal interestAmount) {

        if(interestAmount.compareTo(BigDecimal.ZERO)>=0){
            throw new IllegalFieldException(LoaErrorMessage.INTEREST_AMOUNT_CANNOT_BE_NEGATIVE);
        }
    }

    public void controlIsPrincipalLoanAmountPositive(BigDecimal principalLoanAmount) {

        if(principalLoanAmount.compareTo(BigDecimal.ZERO)>0){
            throw new IllegalFieldException(LoaErrorMessage.PRINCIPAL_lOAN_AMOUNT_MUST_BE_POSITIVE);
        }
    }
}
