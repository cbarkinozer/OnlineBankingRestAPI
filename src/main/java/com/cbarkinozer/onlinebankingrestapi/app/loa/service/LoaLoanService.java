package com.cbarkinozer.onlinebankingrestapi.app.loa.service;

import com.cbarkinozer.onlinebankingrestapi.app.loa.dto.LoaCalculateLoanDto;
import com.cbarkinozer.onlinebankingrestapi.app.loa.dto.LoaCalculateLoanResponseDto;
import com.cbarkinozer.onlinebankingrestapi.app.loa.service.entityservice.LoaLoanEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Transactional
@RequiredArgsConstructor
public class LoaLoanService {

    private final LoaLoanValidationService loaLoanValidationService;

    private final BigDecimal INTEREST_RATE = BigDecimal.valueOf(1.59);
    private final BigDecimal ALLOCATION_FEE = BigDecimal.valueOf(50);

    public LoaCalculateLoanResponseDto calculateLoan(LoaCalculateLoanDto loaCalculateLoanDto) {

        loaLoanValidationService.controlIsParameterNotNull(loaCalculateLoanDto);

        Integer installmentCount = loaCalculateLoanDto.getInstallmentCount();
        BigDecimal principalLoanAmount = loaCalculateLoanDto.getPrincipalLoanAmount();

        BigDecimal subCalculation = INTEREST_RATE.add(BigDecimal.ONE).pow(installmentCount);

        BigDecimal monthlyInstallmentAmount = INTEREST_RATE.multiply(subCalculation)
                .divide(subCalculation.subtract(BigDecimal.ONE), RoundingMode.UP);

        BigDecimal interestAmount = principalLoanAmount.multiply(INTEREST_RATE);
        BigDecimal totalLoanPayment = principalLoanAmount.add(interestAmount).add(ALLOCATION_FEE);

        LoaCalculateLoanResponseDto loaCalculateLoanResponseDto = new LoaCalculateLoanResponseDto();

        loaLoanValidationService.controlIsInterestRateNotNegative(INTEREST_RATE);
        loaLoanValidationService.controlIsInstallmentAmountPositive(monthlyInstallmentAmount);
        loaLoanValidationService.controlIsTotalLoanPaymentPositive(totalLoanPayment);

        loaCalculateLoanResponseDto.setInterestRate(INTEREST_RATE);
        loaCalculateLoanResponseDto.setMonthlyInstallmentAmount(monthlyInstallmentAmount);
        loaCalculateLoanResponseDto.setTotalLoanPayment(totalLoanPayment);

        return loaCalculateLoanResponseDto;
    }
}
