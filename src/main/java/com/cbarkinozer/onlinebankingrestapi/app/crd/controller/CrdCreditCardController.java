package com.cbarkinozer.onlinebankingrestapi.app.crd.controller;

import com.cbarkinozer.onlinebankingrestapi.app.crd.dto.*;
import com.cbarkinozer.onlinebankingrestapi.app.crd.service.CrdCreditCardActivityService;
import com.cbarkinozer.onlinebankingrestapi.app.crd.service.CrdCreditCardService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.dto.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/credit-cards")
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
            summary = "Get credit card activity by price interval.",
            description = "Gets credit card activity in the range by given min and max."
    )
    @GetMapping("/find-activity-by-amount-interval")
    public ResponseEntity<RestResponse<List<CrdCreditCardActivityDto>>>
    findCreditCardActivityByAmountInterval(@RequestParam BigDecimal min, @RequestParam BigDecimal max){

        List<CrdCreditCardActivityDto> crdCreditCardActivityDtoList = crdCreditCardActivityService.findCreditCardActivityByAmountInterval(min,max);

        return ResponseEntity.ok(RestResponse.of(crdCreditCardActivityDtoList));
    }


    @Operation(
            tags = "Credit Card Controller",
            summary = "Get a credit card's activities between dates",
            description = "Date format: YYYY-MM-DD. Gets a credit card's activities between dates pageable."
    )
    @GetMapping("/{creditCardId}/activities")
    public ResponseEntity<RestResponse<List<CrdCreditCardActivityDto>>> findCreditCardActivityBetweenDates(
            @PathVariable Long creditCardId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ){

        List<CrdCreditCardActivityDto> crdCreditCardActivityDtoList =
                crdCreditCardService.findCreditCardActivityBetweenDates(creditCardId, startDate, endDate);

        return ResponseEntity.ok(RestResponse.of(crdCreditCardActivityDtoList));
    }

    @Operation(
            tags = "Credit Card Controller",
            summary = "Get an analysis about credit card activities.",
            description = "Gets an analysis about credit card activity's minimum, maximum, and average amounts, " +
                    "count of credit card activities and credit card activity by credit card activity type."
    )
    @GetMapping("/get-card-activity-analysis/{creditCardId}")
    public ResponseEntity<RestResponse<List<CrdCreditCardActivityAnalysisDto>>> getCardActivityAnalysis(@PathVariable Long creditCardId){

        List<CrdCreditCardActivityAnalysisDto> crdCreditCardActivityAnalysisDtoList = crdCreditCardActivityService.getCardActivityAnalysis(creditCardId);

        return ResponseEntity.ok(RestResponse.of(crdCreditCardActivityAnalysisDtoList));
    }

    @Operation(
            tags = "Credit Card Controller",
            summary = "Get credit card details",
            description = "Get details of your credit card."
    )
    @GetMapping("/{id}/cardDetails")
    public ResponseEntity<RestResponse<CrdCreditCardDetailsDto>> getCardDetails(@PathVariable Long id){

        CrdCreditCardDetailsDto crdCreditCardDetailsDto = crdCreditCardService.getCardDetails(id);

        return ResponseEntity.ok(RestResponse.of(crdCreditCardDetailsDto));
    }

    @Operation(
            tags = "Credit Card Controller",
            summary = "Save a credit card",
            description = "Save a credit card."
    )
    @PostMapping("/save-credit-card")
    public ResponseEntity<RestResponse<MappingJacksonValue>> saveCreditCard(@RequestBody CrdCreditCardSaveDto crdCreditCardSaveDto){

        CrdCreditCardDto crdCreditCardDto = crdCreditCardService.saveCreditCard(crdCreditCardSaveDto);

        WebMvcLinkBuilder linkGet = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(
                        this.getClass()).findCreditCardById(crdCreditCardDto.getId()));

        WebMvcLinkBuilder linkDelete = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(
                        this.getClass()).cancelCreditCard(crdCreditCardDto.getId()));

        EntityModel<CrdCreditCardDto> entityModel = EntityModel.of(crdCreditCardDto);

        entityModel.add(linkGet.withRel("find-by-id"));
        entityModel.add(linkDelete.withRel("cancel"));

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(entityModel);

        return ResponseEntity.ok(RestResponse.of(mappingJacksonValue));
    }

    @Operation(
            tags = "Credit Card Controller",
            summary = "Spend money from credit card.",
            description = "Spend money from credit card."
    )
    @PostMapping("/spend-money")
    public ResponseEntity<RestResponse<CrdCreditCardActivityDto>> spendMoney(@RequestBody CrdCreditCardSpendDto crdCreditCardSpendDto){

        CrdCreditCardActivityDto crdCreditCardActivityDto = crdCreditCardService.spendMoney(crdCreditCardSpendDto);

        return ResponseEntity.ok(RestResponse.of(crdCreditCardActivityDto));
    }

    @Operation(
            tags = "Credit Card Controller",
            summary = "Refund money from credit card.",
            description = "Refund money from credit card."
    )
    @PostMapping("/refund/{activityId}")
    public ResponseEntity<RestResponse<CrdCreditCardActivityDto>> refundMoney(@PathVariable Long activityId){

        CrdCreditCardActivityDto crdCreditCardActivityDto = crdCreditCardService.refundMoney(activityId);

        return ResponseEntity.ok(RestResponse.of(crdCreditCardActivityDto));
    }

    @Operation(
            tags = "Credit Card Controller",
            summary = "Get payment money.",
            description = "Get payment money (card limit increase)."
    )
    @PostMapping("/receive-payment")
    public ResponseEntity<RestResponse<CrdCreditCardActivityDto>> receivePayment(@RequestBody CrdCreditCardPaymentDto crdCreditCardPaymentDto){

        CrdCreditCardActivityDto crdCreditCardActivityDto = crdCreditCardService.receivePayment(crdCreditCardPaymentDto);

        return ResponseEntity.ok(RestResponse.of(crdCreditCardActivityDto));
    }

    @Operation(
            tags = "Credit Card Controller",
            summary = "Cancel a credit card",
            description = "Cancel a credit card by making its status passive."
    )
    @PatchMapping("/{cardId}")
    public ResponseEntity<RestResponse<?>> cancelCreditCard(@PathVariable Long cardId){

        crdCreditCardService.cancelCreditCard(cardId);

        return ResponseEntity.ok(RestResponse.empty());
    }


}
