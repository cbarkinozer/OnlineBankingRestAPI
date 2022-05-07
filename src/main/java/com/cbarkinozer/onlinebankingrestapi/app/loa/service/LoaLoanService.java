package com.cbarkinozer.onlinebankingrestapi.app.loa.service;

import com.cbarkinozer.onlinebankingrestapi.app.loa.dto.*;
import com.cbarkinozer.onlinebankingrestapi.app.loa.entity.LoaLoan;
import com.cbarkinozer.onlinebankingrestapi.app.loa.mapper.LoaLoanMapper;
import com.cbarkinozer.onlinebankingrestapi.app.loa.service.entityservice.LoaLoanEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class LoaLoanService {

    private final LoaLoanValidationService loaLoanValidationService;
    private final LoaLoanEntityService loaLoanEntityService;

    private final BigDecimal INTEREST_RATE = BigDecimal.valueOf(1.59);
    private final BigDecimal ALLOCATION_FEE = BigDecimal.valueOf(50);
    private final BigDecimal KKDV_RATE = BigDecimal.valueOf(0.05);
    private final BigDecimal BSMV_RATE = BigDecimal.valueOf(0.15);

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

    public LoaCalculateLateFeeResponseDto calculateLateFee(LoaCalculateLateFeeDto loaCalculateLateFeeDto) {

        loaLoanValidationService.controlIsParameterNotNull(loaCalculateLateFeeDto);

        BigDecimal totalLoan = loaCalculateLateFeeDto.getTotalLoan();
        Integer lateDayCount = loaCalculateLateFeeDto.getLateDayCount();

        BigDecimal lateFeeRate = INTEREST_RATE.add(INTEREST_RATE.multiply(BigDecimal.valueOf(0.30)));
        BigDecimal totalLateFee = totalLoan.multiply(BigDecimal.valueOf(lateDayCount)).multiply(lateFeeRate)
                .divide(BigDecimal.valueOf(30),RoundingMode.UP);

        BigDecimal totalTax = BSMV_RATE.add(KKDV_RATE);

        BigDecimal lateInterestTax = totalLateFee.multiply(totalTax);
        totalLateFee = totalLateFee.add(lateInterestTax);


        loaLoanValidationService.controlIsLateFeeRateNotNegative(lateFeeRate);
        loaLoanValidationService.controlIsTotalLateFeePositive(totalLateFee);
        loaLoanValidationService.controlIsLateInterestTaxNotNegative(lateInterestTax);


        LoaCalculateLateFeeResponseDto loaCalculateLateFeeResponseDto = new LoaCalculateLateFeeResponseDto();
        loaCalculateLateFeeResponseDto.setLateFeeRate(lateFeeRate);
        loaCalculateLateFeeResponseDto.setTotalLateFee(totalLateFee);
        loaCalculateLateFeeResponseDto.setLateInterestTax(lateInterestTax);

        return loaCalculateLateFeeResponseDto;
    }

    public LoaLoanDto findLoanById(Long id) {

        LoaLoan loaLoan = loaLoanEntityService.getByIdWithControl(id);

        LoaLoanDto loaLoanDto = LoaLoanMapper.INSTANCE.convertToLoaLoanDto(loaLoan);

        return loaLoanDto;
    }

    public LoaLoanDto applyLoan(LoaApplyLoanDto loaLoanApplyLoanDto) {

        loaLoanValidationService.controlIsParameterNotNull(loaLoanApplyLoanDto);

        Long customerId = loaLoanApplyLoanDto.getCustomerId();
        BigDecimal principalLoanAmount = loaLoanApplyLoanDto.getPrincipalLoanAmount();
        Integer installmentCount = loaLoanApplyLoanDto.getInstallmentCount();
        BigDecimal monthlySalary = loaLoanApplyLoanDto.getMonthlySalary();

        LoaLoan loaLoan = LoaLoanMapper.INSTANCE.convertToLoaLoan(loaLoanApplyLoanDto);

        BigDecimal subCalculation = INTEREST_RATE.add(BigDecimal.ONE).pow(installmentCount);

        BigDecimal monthlyInstallmentAmount = INTEREST_RATE.multiply(subCalculation)
                .divide(subCalculation.subtract(BigDecimal.ONE), RoundingMode.UP);

        BigDecimal interestAmount = principalLoanAmount.multiply(INTEREST_RATE);

        BigDecimal maxInstallmentAmount = monthlySalary.multiply(BigDecimal.valueOf(0.5));
        BigDecimal maxLoanAmount = maxInstallmentAmount
                .multiply(BigDecimal.valueOf(installmentCount))
                .multiply(BigDecimal.valueOf(0.80));

        LocalDate dueDate = LocalDate.now().plusMonths(installmentCount);


        loaLoanValidationService.controlIsCustomerExist(customerId);
        loaLoanValidationService.controlIsMonthlyInstallmentAmountPositive(monthlyInstallmentAmount);
        loaLoanValidationService.controlIsInterestAmountNotNegative(interestAmount);
        loaLoanValidationService.controlIsPrincipalLoanAmountPositive(principalLoanAmount);
        loaLoanValidationService.controlIsLoanAmountNotGreaterThanMaxLoanAmount(
                principalLoanAmount, maxLoanAmount);


        loaLoan.setMonthlyInstallmentAmount(monthlyInstallmentAmount);
        loaLoan.setInterestToBePaid(interestAmount);
        loaLoan.setPrincipalToBePaid(principalLoanAmount);
        loaLoan.setRemainingPrincipal(principalLoanAmount);
        loaLoan.setDueDate(dueDate);


        loaLoan = loaLoanEntityService.save(loaLoan);

        LoaLoanDto loaLoanDto = LoaLoanMapper.INSTANCE.convertToLoaLoanDto(loaLoan);

        return loaLoanDto;
    }


}
