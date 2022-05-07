package com.cbarkinozer.onlinebankingrestapi.app.loa.controller;

import com.cbarkinozer.onlinebankingrestapi.app.loa.dto.*;
import com.cbarkinozer.onlinebankingrestapi.app.loa.service.LoaLoanService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.dto.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<RestResponse<LoaCalculateLoanResponseDto>> calculateLoan(@RequestBody LoaCalculateLoanDto loaCalculateLoanDto){

        LoaCalculateLoanResponseDto loaCalculateLoanResponseDto = loaLoanService.calculateLoan(loaCalculateLoanDto);

        return ResponseEntity.ok(RestResponse.of(loaCalculateLoanResponseDto));
    }

    @Operation(
            tags = "Loan Controller",
            summary = "Calculate late fee.",
            description = "Calculate the late fee."
    )
    @GetMapping("/calculate-late-fee")
    public ResponseEntity<RestResponse<LoaCalculateLateFeeResponseDto>> calculateLateFee(@RequestBody LoaCalculateLateFeeDto loaCalculateLateFeeDto){

        LoaCalculateLateFeeResponseDto loaCalculateLateFeeResponseDto = loaLoanService.calculateLateFee(loaCalculateLateFeeDto);

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
            description = "Pay installment."
    )
    @PostMapping("/pay-installment")
    public ResponseEntity<RestResponse<LoaPayInstallmentResponseDto>> payInstallment(@RequestBody LoaPayInstallmentDto loaPayInstallmentDto){

        LoaPayInstallmentResponseDto loaPayInstallmentResponseDto = loaLoanService.payInstallment(loaPayInstallmentDto);

        return ResponseEntity.ok(RestResponse.of(loaPayInstallmentResponseDto));
    }




}
