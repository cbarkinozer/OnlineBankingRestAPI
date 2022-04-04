package com.cbarkinozer.onlinebankingrestapi.app.acc.controller;

import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccAccountDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccAccountSaveDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccMoneyTransferDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccMoneyTransferSaveDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.AccAccountService;
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

    @GetMapping
    public ResponseEntity<RestResponse<List<AccAccountDto>>> findAllAccounts(Optional<Integer> pageOptional,
                                                                       Optional<Integer> sizeOptional){

        List<AccAccountDto> accAccountDtoList = accAccountService.findAllAccounts(pageOptional, sizeOptional);

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
    

}
