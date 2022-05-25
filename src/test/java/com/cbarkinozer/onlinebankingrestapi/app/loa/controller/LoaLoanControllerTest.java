package com.cbarkinozer.onlinebankingrestapi.app.loa.controller;

import com.cbarkinozer.onlinebankingrestapi.app.gen.dto.RestResponse;
import com.cbarkinozer.onlinebankingrestapi.app.loa.dto.*;
import com.cbarkinozer.onlinebankingrestapi.app.loa.enums.LoaLoanStatusType;
import com.cbarkinozer.onlinebankingrestapi.app.loa.service.LoaLoanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoaLoanControllerTest {

    @Mock
    private LoaLoanService loaLoanService;

    @InjectMocks
    private LoaLoanController loaLoanController;

    @Test
    void shouldCalculateLoan() {

        LoaCalculateLoanResponseDto loaCalculateLoanResponseDto = createDummyloaCalculateLoanResponseDto();

        Integer installmentCount = 24;
        BigDecimal principalLoanAmount = BigDecimal.valueOf(3000);

        when(loaLoanService.calculateLoan(installmentCount,principalLoanAmount)).thenReturn(loaCalculateLoanResponseDto);

        ResponseEntity<RestResponse<LoaCalculateLoanResponseDto>> result = loaLoanController.calculateLoan(installmentCount, principalLoanAmount );

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),loaCalculateLoanResponseDto);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);

    }

    private LoaCalculateLoanResponseDto createDummyloaCalculateLoanResponseDto() {

        LoaCalculateLoanResponseDto loaCalculateLoanResponseDto = mock(LoaCalculateLoanResponseDto.class);

        loaCalculateLoanResponseDto.setInterestRate(BigDecimal.valueOf(0.0159));
        loaCalculateLoanResponseDto.setAnnualCostRate(BigDecimal.valueOf(0.1908));
        loaCalculateLoanResponseDto.setTotalInterest(BigDecimal.valueOf(1144.8));
        loaCalculateLoanResponseDto.setMonthlyInstallmentAmount(BigDecimal.valueOf(174.575));
        loaCalculateLoanResponseDto.setTotalPayment(BigDecimal.valueOf(4189.8));
        loaCalculateLoanResponseDto.setAllocationFee(BigDecimal.valueOf(45));

        return  loaCalculateLoanResponseDto;
    }

    private LoaLoanDto createDummyLoaLoanDto() {

        LoaLoanDto loaLoanDto = mock(LoaLoanDto.class);

        loaLoanDto.setId(1L);
        loaLoanDto.setCustomerId(1L);
        loaLoanDto.setPrincipalLoanAmount(BigDecimal.valueOf(3000));
        loaLoanDto.setMonthlyInstallmentAmount(BigDecimal.valueOf(125));
        loaLoanDto.setInstallmentCount(24);
        loaLoanDto.setPrincipalToBePaid(BigDecimal.valueOf(3000));
        loaLoanDto.setRemainingPrincipal(BigDecimal.valueOf(3000));
        loaLoanDto.setDueDate(LocalDate.now().plusMonths(24));

        return  loaLoanDto;
    }

    @Test
    void shouldCalculateLateFee() {

        LoaCalculateLateFeeResponseDto loaCalculateLateFeeResponseDto = createDummyloaCalculateLateFeeResponseDto();

        when(loaLoanService.calculateLateFee(1L)).thenReturn(loaCalculateLateFeeResponseDto);

        ResponseEntity<RestResponse<LoaCalculateLateFeeResponseDto>> result = loaLoanController.calculateLateFee(1L);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),loaCalculateLateFeeResponseDto);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    private LoaCalculateLateFeeResponseDto createDummyloaCalculateLateFeeResponseDto() {

        LoaCalculateLateFeeResponseDto loaCalculateLateFeeResponseDto = mock(LoaCalculateLateFeeResponseDto.class);

        loaCalculateLateFeeResponseDto.setTotalLateFee(BigDecimal.valueOf(57.24));
        loaCalculateLateFeeResponseDto.setLateDayCount(60L);
        loaCalculateLateFeeResponseDto.setLateInterestTax(BigDecimal.valueOf(3000));
        loaCalculateLateFeeResponseDto.setLateFeeRate(BigDecimal.valueOf(125));

        return  loaCalculateLateFeeResponseDto;
    }

    @Test
    void shouldFindLoanById() {

        LoaLoanDto loaLoanDto = createDummyLoaLoanDto();

        when(loaLoanService.findLoanById(1L)).thenReturn(loaLoanDto);

        ResponseEntity<RestResponse<LoaLoanDto>> result = loaLoanController.findLoanById(1L);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),loaLoanDto);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    @Test
    void shouldApplyLoan() {

        LoaApplyLoanDto loaApplyLoanDto = createDummyLoaApplyLoanDto();
        LoaLoanDto loaLoanDto = createDummyLoaLoanDto();

        when(loaLoanService.applyLoan(loaApplyLoanDto)).thenReturn(loaLoanDto);

        ResponseEntity<RestResponse<LoaLoanDto>> result = loaLoanController.applyLoan(loaApplyLoanDto);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),loaLoanDto);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);

    }

    private LoaApplyLoanDto createDummyLoaApplyLoanDto() {

        LoaApplyLoanDto loaApplyLoanDto = mock(LoaApplyLoanDto.class);

        loaApplyLoanDto.setMonthlySalary(BigDecimal.valueOf(3000));
        loaApplyLoanDto.setPrincipalLoanAmount(BigDecimal.valueOf(3000));
        loaApplyLoanDto.setInstallmentCount(24);

        return  loaApplyLoanDto;
    }

    @Test
    void shouldPayInstallment() {

        LoaPayInstallmentResponseDto loaPayInstallmentResponseDto = createLoaPayInstallmentResponseDto();

        when(loaLoanService.payInstallment(1L)).thenReturn(loaPayInstallmentResponseDto);

        ResponseEntity<RestResponse<LoaPayInstallmentResponseDto>> result = loaLoanController.payInstallment(1L);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),loaPayInstallmentResponseDto);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    private LoaPayInstallmentResponseDto createLoaPayInstallmentResponseDto() {

        LoaPayInstallmentResponseDto loaPayInstallmentResponseDto = mock(LoaPayInstallmentResponseDto.class);

        loaPayInstallmentResponseDto.setLoanId(1L);
        loaPayInstallmentResponseDto.setPaymentDate(LocalDate.now());
        loaPayInstallmentResponseDto.setRemainingPrincipal(BigDecimal.valueOf(3000));
        loaPayInstallmentResponseDto.setDueDate(LocalDate.now().plusMonths(24));

        return  loaPayInstallmentResponseDto;
    }

    @Test
    void shouldPayLoanOff() {

        LoaPayLoanOffResponseDto loaPayLoanOffResponseDto = createLoaPayLoanOffResponseDto();

        when(loaLoanService.payLoanOff(1L)).thenReturn(loaPayLoanOffResponseDto);

        ResponseEntity<RestResponse<LoaPayLoanOffResponseDto>> result = loaLoanController.payLoanOff(1L);

        assertTrue(Objects.requireNonNull(result.getBody()).isSuccess());
        assertEquals(result.getBody().getData(),loaPayLoanOffResponseDto);
        assertNull(result.getBody().getMessage());
        assertNotNull(result);
    }

    private LoaPayLoanOffResponseDto createLoaPayLoanOffResponseDto() {

        LoaPayLoanOffResponseDto loaPayLoanOffResponseDto = mock(LoaPayLoanOffResponseDto.class);

        loaPayLoanOffResponseDto.setId(1L);
        loaPayLoanOffResponseDto.setCustomerId(1L);
        loaPayLoanOffResponseDto.setPaidAmount(BigDecimal.valueOf(1000));
        loaPayLoanOffResponseDto.setRemainingAmount(BigDecimal.valueOf(1000));
        loaPayLoanOffResponseDto.setLoanStatusType(LoaLoanStatusType.PAID);

        return  loaPayLoanOffResponseDto;

    }
}