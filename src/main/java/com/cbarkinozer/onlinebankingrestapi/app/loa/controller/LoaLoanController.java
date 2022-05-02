package com.cbarkinozer.onlinebankingrestapi.app.loa.controller;

import com.cbarkinozer.onlinebankingrestapi.app.loa.dto.LoaApplyLoanDto;
import com.cbarkinozer.onlinebankingrestapi.app.loa.dto.LoaCalculateLoanDto;
import com.cbarkinozer.onlinebankingrestapi.app.loa.dto.LoaCalculateLoanResponseDto;
import com.cbarkinozer.onlinebankingrestapi.app.loa.dto.LoaLoanDto;
import com.cbarkinozer.onlinebankingrestapi.app.loa.service.LoaLoanService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.dto.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    public ResponseEntity<RestResponse<LoaCalculateLoanResponseDto>> calculateLoan(LoaCalculateLoanDto loaCalculateLoanDto){

        LoaCalculateLoanResponseDto loaCalculateLoanResponseDto = loaLoanService.calculateLoan(loaCalculateLoanDto);

        return ResponseEntity.ok(RestResponse.of(loaCalculateLoanResponseDto));
    }

    

}
