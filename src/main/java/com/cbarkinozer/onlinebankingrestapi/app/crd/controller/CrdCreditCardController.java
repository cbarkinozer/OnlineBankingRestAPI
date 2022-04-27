package com.cbarkinozer.onlinebankingrestapi.app.crd.controller;

import com.cbarkinozer.onlinebankingrestapi.app.crd.dto.CrdCreditCardActivityDto;
import com.cbarkinozer.onlinebankingrestapi.app.crd.dto.CrdCreditCardDto;
import com.cbarkinozer.onlinebankingrestapi.app.crd.dto.CrdCreditCardSaveDto;
import com.cbarkinozer.onlinebankingrestapi.app.crd.service.CrdCreditCardActivityService;
import com.cbarkinozer.onlinebankingrestapi.app.crd.service.CrdCreditCardService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.dto.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/credit-card")
public class CrdCreditCardController {

    private final CrdCreditCardService crdCreditCardService;
    private final CrdCreditCardActivityService crdCreditCardActivityService;

    @Operation(
            tags = "Credit Card Controller",
            summary = "All credit cards",
            description = "Gets all active credit cards."
    )
    @GetMapping
    public ResponseEntity<RestResponse<List<CrdCreditCardDto>>> findAllCreditCards(){

        List<CrdCreditCardDto> crdCreditCardDtoList = crdCreditCardService.findAllCreditCards();

        return ResponseEntity.ok(RestResponse.of(crdCreditCardDtoList));
    }

    @Operation(
            tags = "Credit Card Controller",
            summary = "Get a credit card",
            description = "Gets a credit card by id."
    )
    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<CrdCreditCardDto>> findCreditCardById(@PathVariable Long id){

        CrdCreditCardDto crdCreditCardDto = crdCreditCardService.findCreditCardById(id);

        return ResponseEntity.ok(RestResponse.of(crdCreditCardDto));
    }

    @Operation(
            tags = "Credit Card Controller",
            summary = "Get credit card by price interval",
            description = "Gets products in the range by given min and max."
    )
    @GetMapping("/find-by-price-interval")
    public ResponseEntity<RestResponse<List<CrdCreditCardActivityDto>>>
    findCreditCardActivityByAmountInterval(@RequestParam BigDecimal min, @RequestParam BigDecimal max){

        List<CrdCreditCardActivityDto> crdCreditCardActivityDtoList = crdCreditCardActivityService.findCreditCardActivityByAmountInterval(min,max);

        return ResponseEntity.ok(RestResponse.of(crdCreditCardActivityDtoList));
    }

    @Operation(
            tags = "Credit Card Controller",
            summary = "Save a credit card",
            description = "Save a credit card."
    )
    @PostMapping
    public ResponseEntity<RestResponse<CrdCreditCardDto>> saveCreditCard(@RequestBody CrdCreditCardSaveDto crdCreditCardSaveDto){

        CrdCreditCardDto crdCreditCardDto = crdCreditCardService.saveCreditCard(crdCreditCardSaveDto);

        return ResponseEntity.ok(RestResponse.of(crdCreditCardDto));
    }


    @Operation(
            tags = "Credit Card Controller",
            summary = "Cancel a credit card",
            description = "Cancel a credit card by making its status passive."
    )
    @PatchMapping("/cancel/{cardId}")
    public ResponseEntity<RestResponse<?>> cancelCreditCard(@PathVariable Long cardId){

        crdCreditCardService.cancelCreditCard(cardId);

        return ResponseEntity.ok(RestResponse.empty());
    }


}
