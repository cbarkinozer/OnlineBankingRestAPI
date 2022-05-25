package com.cbarkinozer.onlinebankingrestapi.app.acc.controller;

import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.*;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.AccAccountActivityService;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.AccAccountService;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.AccMoneyTransferService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.dto.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/accounts")
public class AccAccountController {

    private final AccAccountService accAccountService;
    private final AccMoneyTransferService accMoneyTransferService;
    private final AccAccountActivityService accAccountActivityService;

    @Operation(
            tags = "Account Controller",
            summary = "All Accounts",
            description = "Gets all accounts."
    )
    @GetMapping
    public ResponseEntity<RestResponse<List<AccAccountDto>>> findAllAccounts(){

        List<AccAccountDto> accAccountDtoList = accAccountService.findAllAccounts();

        return ResponseEntity.ok(RestResponse.of(accAccountDtoList));
    }

    @Operation(
            tags = "Account Controller",
            summary = "Get an Account",
            description = "Gets an account by id."
    )
    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<AccAccountDto>> findAccountById(@PathVariable Long id){

        AccAccountDto accAccountDto = accAccountService.findAccountById(id);

        return ResponseEntity.ok(RestResponse.of(accAccountDto));
    }

    @Operation(
            tags = "Account Controller",
            summary = "Get an Account",
            description = "Gets a account by customer id."
    )
    @GetMapping("/account/customerId/{customerId}")
    public ResponseEntity<RestResponse<List<AccAccountDto>>> findAccountByCustomerId(@PathVariable Long customerId){

        List<AccAccountDto> accAccountDtoList = accAccountService.findAccountByCustomerId(customerId);

        return ResponseEntity.ok(RestResponse.of(accAccountDtoList));
    }

    @Operation(
            tags = "Account Controller",
            summary = "Save an Account",
            description = "Gets an account by customer id."
    )
    @PostMapping("/save-account")
    public ResponseEntity<RestResponse<MappingJacksonValue>> saveAccount(@RequestBody AccAccountSaveDto accAccountSaveDto){

        AccAccountDto accAccountDto = accAccountService.saveAccount(accAccountSaveDto);

        WebMvcLinkBuilder linkGet = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(
                        this.getClass()).findAccountById(accAccountDto.getId()));

        WebMvcLinkBuilder linkDelete = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(
                        this.getClass()).cancelAccount(accAccountDto.getId()));

        EntityModel<AccAccountDto> entityModel = EntityModel.of(accAccountDto);

        entityModel.add(linkGet.withRel("find-by-id"));
        entityModel.add(linkDelete.withRel("delete"));

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(entityModel);

        return ResponseEntity.ok(RestResponse.of(mappingJacksonValue));
    }

    @Operation(
            tags = "Account Controller",
            summary = "Cancel an Account",
            description = "Deletes a user by canceling (setting the status type passive) by id."
    )
    @PatchMapping("/{id}")
    public ResponseEntity<RestResponse<?>> cancelAccount(@PathVariable Long id){

        accAccountService.cancelAccount(id);

        return ResponseEntity.ok(RestResponse.empty());
    }

    @Operation(
            tags = "Account Controller",
            summary = "Transfer money",
            description = "Transfers money between acccounts."
    )
    @PostMapping("/transfer-money")
    public ResponseEntity<RestResponse<AccMoneyTransferDto>> transferMoney(
            @RequestBody AccMoneyTransferSaveDto accMoneyTransferSaveDto){

        AccMoneyTransferDto accMoneyTransferDto = accMoneyTransferService.transferMoney(accMoneyTransferSaveDto);

        return ResponseEntity.ok(RestResponse.of(accMoneyTransferDto));
    }

    @Operation(
            tags = "Account Controller",
            summary = "Withdraw from an account",
            description = "Withdraw money from an account."
    )
    @PostMapping("/withdraw")
    public ResponseEntity<RestResponse<AccAccountActivityDto>> withdraw(@RequestBody AccMoneyActivityRequestDto accMoneyActivityRequestDto){

        AccAccountActivityDto accAccountActivityDto = accAccountActivityService.withdraw(accMoneyActivityRequestDto);

        return ResponseEntity.ok(RestResponse.of(accAccountActivityDto));
    }

    @Operation(
            tags = "Account Controller",
            summary = "Deposit to an account",
            description = "Deposit money to an account."
    )
    @PostMapping("/deposit")
    public ResponseEntity<RestResponse<AccAccountActivityDto>> deposit(@RequestBody AccMoneyActivityRequestDto accMoneyActivityRequestDto){

        AccAccountActivityDto accAccountActivityDto = accAccountActivityService.deposit(accMoneyActivityRequestDto);

        return ResponseEntity.ok(RestResponse.of(accAccountActivityDto));
    }

}
