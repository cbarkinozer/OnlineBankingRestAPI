package com.cbarkinozer.onlinebankingrestapi.app.loa.service;

import com.cbarkinozer.onlinebankingrestapi.app.loa.dto.LoaCalculateLateFeeResponseDto;
import com.cbarkinozer.onlinebankingrestapi.app.loa.dto.LoaCalculateLoanResponseDto;
import com.cbarkinozer.onlinebankingrestapi.app.loa.dto.LoaLoanDto;
import com.cbarkinozer.onlinebankingrestapi.app.loa.entity.LoaLoan;
import com.cbarkinozer.onlinebankingrestapi.app.loa.entity.LoaLoanPayment;
import com.cbarkinozer.onlinebankingrestapi.app.loa.service.entityservice.LoaLoanEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.loa.service.entityservice.LoaLoanPaymentEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoaLoanServiceTest {

    @Mock
    private LoaLoanEntityService loaLoanEntityService;

    @Mock
    private LoaLoanPaymentEntityService loaLoanPaymentEntityService;

    @InjectMocks
    private LoaLoanService loaLoanService;

    @Test
    void shouldCalculateLoan() {

        LoaCalculateLoanResponseDto result = loaLoanService.calculateLoan(24, BigDecimal.valueOf(3000));

        assertNotNull(result);
    }

    @Test
    void shouldCalculateLateFee() {

        LoaCalculateLateFeeResponseDto result = loaLoanService.calculateLateFee(1L);

        assertNotNull(result);
    }

    @Test
    void shouldFindLoanById() {

        Long id = 1L;

        LoaLoan loaLoan = mock(LoaLoan.class);
        when(loaLoan.getId()).thenReturn(id);

        when(loaLoanEntityService.getByIdWithControl(id)).thenReturn(loaLoan);

        LoaLoanDto loaLoanDto = loaLoanService.findLoanById(id);

        assertEquals(id, loaLoanDto.getId());
    }

    @Test
    void shouldApplyLoan() {

        LoaLoan loaLoan = mock(LoaLoan.class);

        when(loaLoan.getId()).thenReturn(1L);

        when(loaLoanEntityService.save(any())).thenReturn(loaLoan);

        LoaLoan result = loaLoanEntityService.save(loaLoan);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldPayInstallment() {

        LoaLoan loaLoan = mock(LoaLoan.class);
        LoaLoanPayment loaLoanPayment = mock(LoaLoanPayment.class);

        when(loaLoan.getId()).thenReturn(1L);

        when(loaLoanEntityService.save(loaLoan)).thenReturn(loaLoan);
        when(loaLoanPaymentEntityService.save(loaLoanPayment)).thenReturn(loaLoanPayment);

        LoaLoan loaLoanResult = loaLoanEntityService.save(loaLoan);
        LoaLoanPayment loaLoanPaymentResult = loaLoanPaymentEntityService.save(loaLoanPayment);

        assertEquals(1L, loaLoanResult.getId());
        assertEquals(1L, loaLoanPaymentResult.getId());
    }

    @Test
    void shouldPayLoanOff() {

        LoaLoan loaLoan = mock(LoaLoan.class);

        when(loaLoanEntityService.getByIdWithControl(anyLong())).thenReturn(loaLoan);

        loaLoanService.payLoanOff(anyLong());

        verify(loaLoanEntityService).getByIdWithControl(anyLong());
        verify(loaLoanEntityService).save(any());
    }
}