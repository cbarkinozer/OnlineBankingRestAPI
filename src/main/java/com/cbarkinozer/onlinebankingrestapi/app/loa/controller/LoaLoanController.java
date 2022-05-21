package com.cbarkinozer.onlinebankingrestapi.app.loa.controller;

import com.cbarkinozer.onlinebankingrestapi.app.loa.dto.*;
import com.cbarkinozer.onlinebankingrestapi.app.loa.service.LoaLoanService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.dto.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/loans")
public class LoaLoanController {

    private final LoaLoanService loaLoanService;

    @Operation(
            tags = "Loan Controller",
            summary = "Calculate loan.",
            description = "Calculate the loan."
    )
    @GetMapping("/calculate-loan")
    public ResponseEntity<RestResponse<LoaCalculateLoanResponseDto>> calculateLoan(@RequestParam Integer installmentCount, @RequestParam BigDecimal principalLoanAmount ){

        LoaCalculateLoanResponseDto loaCalculateLoanResponseDto = loaLoanService.calculateLoan(installmentCount,principalLoanAmount);

        return ResponseEntity.ok(RestResponse.of(loaCalculateLoanResponseDto));
    }

    @Operation(
            tags = "Loan Controller",
            summary = "Calculate late fee.",
            description = "Calculate the late fee."
    )
    @GetMapping("/calculate-late-fee/{id}")
    public ResponseEntity<RestResponse<LoaCalculateLateFeeResponseDto>> calculateLateFee(@PathVariable Long id){

        LoaCalculateLateFeeResponseDto loaCalculateLateFeeResponseDto = loaLoanService.calculateLateFee(id);

        return ResponseEntity.ok(RestResponse.of(loaCalculateLateFeeResponseDto));
    }

    @Operation(
            tags = "Loan Controller",
            summary = "Get a Loan",
            description = "Gets a loan by id."
    )
    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<LoaLoanDto>> findLoanById(@PathVariable Long id){

        LoaLoanDto loaLoanDto = loaLoanService.findLoanById(id);

        return ResponseEntity.ok(RestResponse.of(loaLoanDto));
    }

    @Operation(
            tags = "Loan Controller",
            summary = "Apply loan.",
            description = "Apply for a loan."
    )
    @PostMapping("/apply-loan")
    public ResponseEntity<RestResponse<LoaLoanDto>> applyLoan(@RequestBody LoaApplyLoanDto loaApplyLoanDto){

        LoaLoanDto loaLoanDto = loaLoanService.applyLoan(loaApplyLoanDto);

        return ResponseEntity.ok(RestResponse.of(loaLoanDto));
    }

    @Operation(
            tags = "Loan Controller",
            summary = "Pay installment.",
            description = "Pay installment of the loan."
    )
    @PostMapping("/pay-installment/{id}")
    public ResponseEntity<RestResponse<LoaPayInstallmentResponseDto>> payInstallment(@PathVariable Long id){

        LoaPayInstallmentResponseDto loaPayInstallmentResponseDto = loaLoanService.payInstallment(id);

        return ResponseEntity.ok(RestResponse.of(loaPayInstallmentResponseDto));
    }

    @Operation(
            tags="Loan Controller",
            summary = "Pay loan off",
            description = "Pay the remaining amount and close the loan. "
    )
    @DeleteMapping("/pay-loan-off/{id}")
    public ResponseEntity<RestResponse<LoaPayLoanOffResponseDto>> payLoanOff(@PathVariable Long id){

        LoaPayLoanOffResponseDto loaPayLoanOffResponseDto = loaLoanService.payLoanOff(id);

        return ResponseEntity.ok(RestResponse.of(loaPayLoanOffResponseDto));
    }

}
