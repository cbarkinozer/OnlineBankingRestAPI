package com.cbarkinozer.onlinebankingrestapi.app.acc.controller;

import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.*;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.AccAccountActivityService;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.AccAccountService;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.AccMoneyTransferService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.dto.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/accounts")
public class AccAccountController {

    private final AccAccountService accAccountService;
    private final AccMoneyTransferService accMoneyTransferService;
    private final AccAccountActivityService accAccountActivityService;

    @GetMapping
    public ResponseEntity<RestResponse<List<AccAccountDto>>> findAllAccounts(){

        List<AccAccountDto> accAccountDtoList = accAccountService.findAllAccounts();

        return ResponseEntity.ok(RestResponse.of(accAccountDtoList));
    }

    @GetMapping("/account/id/{id}")
    public ResponseEntity<RestResponse<AccAccountDto>> findAccountById(@PathVariable Long id){

        AccAccountDto accAccountDto = accAccountService.findAccountById(id);

        return ResponseEntity.ok(RestResponse.of(accAccountDto));
    }

    @GetMapping("/account/customerId/{customerId}")
    public ResponseEntity<RestResponse<AccAccountDto>> findAccountByCustomerId(@PathVariable Long customerId){

        AccAccountDto accAccountDto = accAccountService.findAccountByCustomerId(customerId);

        return ResponseEntity.ok(RestResponse.of(accAccountDto));
    }

    @PostMapping("/save-account")
    public ResponseEntity<RestResponse<AccAccountDto>> saveAccount(@RequestBody AccAccountSaveDto accAccountSaveDto){

        AccAccountDto accAccountDto = accAccountService.saveAccount(accAccountSaveDto);

        return ResponseEntity.ok(RestResponse.of(accAccountDto));
    }

    @PatchMapping("cancel-account/{id}")
    public ResponseEntity<RestResponse<?>> cancelAccount(@PathVariable Long id){

        accAccountService.cancelAccount(id);

        return ResponseEntity.ok(RestResponse.empty());
    }

    @PostMapping("/transfer-money")
    public ResponseEntity<RestResponse<AccMoneyTransferDto>> transferMoney(
            @RequestBody AccMoneyTransferSaveDto accMoneyTransferSaveDto){

        AccMoneyTransferDto accMoneyTransferDto = accMoneyTransferService.transferMoney(accMoneyTransferSaveDto);

        return ResponseEntity.ok(RestResponse.of(accMoneyTransferDto));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<RestResponse<AccAccountActivityDto>> withdraw(@RequestBody AccMoneyActivityRequestDto accMoneyActivityRequestDto){

        AccAccountActivityDto accAccountActivityDto = accAccountActivityService.withdraw(accMoneyActivityRequestDto);

        return ResponseEntity.ok(RestResponse.of(accAccountActivityDto));
    }

    @PostMapping("/deposit")
    public ResponseEntity<RestResponse<AccAccountActivityDto>> deposit(@RequestBody AccMoneyActivityRequestDto accMoneyActivityRequestDto){

        AccAccountActivityDto accAccountActivityDto = accAccountActivityService.deposit(accMoneyActivityRequestDto);

        return ResponseEntity.ok(RestResponse.of(accAccountActivityDto));
    }

}
