package com.cbarkinozer.onlinebankingrestapi.app.crd.controller;

import com.cbarkinozer.onlinebankingrestapi.app.crd.dto.CrdCreditCardDto;
import com.cbarkinozer.onlinebankingrestapi.app.crd.service.CrdCreditCardService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.dto.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/credit-card")
public class CrdCreditCardController {

    private final CrdCreditCardService crdCreditCardService;

    @Operation(
            tags = "Credit Card Controller",
            summary = "All Credit Cards",
            description = "Gets all active credit cards."
    )
    @GetMapping
    public ResponseEntity findAllCreditCards(){

        List<CrdCreditCardDto> crdCreditCardDtoList = crdCreditCardService.findAllCreditCards();

        return ResponseEntity.ok(RestResponse.of(crdCreditCardDtoList));
    }


}
