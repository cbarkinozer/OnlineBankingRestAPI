package com.cbarkinozer.onlinebankingrestapi.app.loa.service;

import com.cbarkinozer.onlinebankingrestapi.app.cus.service.entityservice.CusCustomerEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.IllegalFieldException;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.ItemNotFoundException;
import com.cbarkinozer.onlinebankingrestapi.app.loa.dto.*;
import com.cbarkinozer.onlinebankingrestapi.app.loa.entity.LoaLoan;
import com.cbarkinozer.onlinebankingrestapi.app.loa.enums.LoaErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.loa.enums.LoaLoanStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class LoaLoanValidationService {

    private final CusCustomerEntityService cusCustomerEntityService;

    public void controlIsParameterNotNull(Integer installmentCount, BigDecimal principalLoanAmount) {

        boolean hasNull = installmentCount == null || principalLoanAmount == null;

        if(hasNull){
            throw new IllegalFieldException(LoaErrorMessage.PARAMETER_CANNOT_BE_NULL);
        }
    }

    public void controlIsParameterNotNull(LoaApplyLoanDto loaApplyLoanDto) {

        boolean hasNull = loaApplyLoanDto.getInstallmentCount() == null     ||
                loaApplyLoanDto.getPrincipalLoanAmount() == null  ||
                loaApplyLoanDto.getMonthlySalary() == null;

        if(hasNull){
            throw new IllegalFieldException(LoaErrorMessage.PARAMETER_CANNOT_BE_NULL);
        }
    }

    public void controlIsInterestRateNotNegative(BigDecimal interestRate) {

        if(interestRate.compareTo(BigDecimal.ZERO)<0){
            throw new IllegalFieldException(LoaErrorMessage.INTEREST_RATE_CANNOT_BE_NEGATIVE);
        }
    }


    public void controlIsInstallmentAmountPositive(BigDecimal monthlyInstallmentAmount) {

        if(monthlyInstallmentAmount.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalFieldException(LoaErrorMessage.INSTALLMENT_AMOUNT_MUST_BE_POSITIVE);
        }
    }

    public void controlIsTotalPaymentPositive(BigDecimal totalPayment) {

        if(totalPayment.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalFieldException(LoaErrorMessage.TOTAL_PAYMENT_MUST_BE_POSITIVE);
        }
    }

    public void controlIsLateFeeRateNotNegative(BigDecimal lateFeeRate) {

        if(lateFeeRate.compareTo(BigDecimal.ZERO)<0){
            throw new IllegalFieldException(LoaErrorMessage.LATE_FEE_RATE_CANNOT_BE_NEGATIVE);
        }
    }

    public void controlIsTotalLateFeePositive(BigDecimal totalLateFee) {

        if(totalLateFee.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalFieldException(LoaErrorMessage.TOTAL_LATE_FEE_MUST_BE_POSITIVE);
        }
    }

    public void controlIsLateInterestTaxNotNegative(BigDecimal lateInterestTax) {

        if(lateInterestTax.compareTo(BigDecimal.ZERO)<0){
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

        if(monthlyInstallmentAmount.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalFieldException(LoaErrorMessage.MONTHLY_INSTALLMENT_AMOUNT_MUST_BE_POSITIVE);
        }
    }

    public void controlIsInterestAmountNotNegative(BigDecimal interestAmount) {

        if(interestAmount.compareTo(BigDecimal.ZERO)<0){
            throw new IllegalFieldException(LoaErrorMessage.INTEREST_AMOUNT_CANNOT_BE_NEGATIVE);
        }
    }

    public void controlIsPrincipalLoanAmountPositive(BigDecimal principalLoanAmount) {

        if(principalLoanAmount.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalFieldException(LoaErrorMessage.PRINCIPAL_lOAN_AMOUNT_MUST_BE_POSITIVE);
        }
    }

    public Long controlIsLoanDueDatePast(LocalDate dueDate) {

        LocalDate now = LocalDate.now();
        long lateDayCount = ChronoUnit.DAYS.between(dueDate, now);

        if(lateDayCount < 1 ){

            LoaErrorMessage loaErrorMessage = LoaErrorMessage.DUE_DATE_HAS_NOT_PASSED_YET;
            loaErrorMessage.setDetailMessage(String.valueOf(dueDate));

            throw new IllegalFieldException(loaErrorMessage);
        }
        return lateDayCount;
    }

    public void controlIsRemainingPrincipalNotNegative(BigDecimal remainingPrincipal) {
        if(remainingPrincipal.compareTo(BigDecimal.ZERO)<0){
            throw new IllegalFieldException(LoaErrorMessage.REMAINING_PRINCIPAL_MUST_BE_POSITIVE);
        }
    }

    public void controlIsTaxRateNotNegative(BigDecimal taxRate) {
        if(taxRate.compareTo(BigDecimal.ZERO)<0){
            throw new IllegalFieldException(LoaErrorMessage.TAX_RATE_CANNOT_BE_NEGATIVE);
        }
    }

    public void controlIsInstallmentCountNotGreaterThanInstallmentCountLimit(int installmentCount, int installmentCountLimit) {
        if(installmentCount>installmentCountLimit){

            LoaErrorMessage loaErrorMessage = LoaErrorMessage.INSTALLMENT_COUNT_CANNOT_BE_LARGER_THAN_LIMIT;
            loaErrorMessage.setDetailMessage(String.valueOf(installmentCountLimit));

            throw new IllegalFieldException(loaErrorMessage);
        }
    }

    public void controlIsLoanNotAlreadyPaidOff(LoaLoan loaLoan) {
        if(loaLoan.getLoanStatusType() == LoaLoanStatusType.PAID){
            throw new IllegalFieldException(LoaErrorMessage.LOAN_ALREADY_PAID_OFF);
        }
    }
}
