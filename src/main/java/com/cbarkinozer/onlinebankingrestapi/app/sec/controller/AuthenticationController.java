package com.cbarkinozer.onlinebankingrestapi.app.sec.controller;

import com.cbarkinozer.onlinebankingrestapi.app.cus.dto.CusCustomerDto;
import com.cbarkinozer.onlinebankingrestapi.app.cus.dto.CusCustomerSaveDto;
import com.cbarkinozer.onlinebankingrestapi.app.gen.dto.RestResponse;
import com.cbarkinozer.onlinebankingrestapi.app.sec.dto.SecLoginRequestDto;
import com.cbarkinozer.onlinebankingrestapi.app.sec.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(tags = "Authentication Controller")
    @PostMapping("/login")
    public ResponseEntity<RestResponse<String>> login(@RequestBody SecLoginRequestDto secLoginRequestDto){

        String token = authenticationService.login(secLoginRequestDto);

        return ResponseEntity.ok(RestResponse.of(token));
    }

    @Operation(tags = "Authentication Controller")
    @PostMapping("/register")
    public ResponseEntity<RestResponse<CusCustomerDto>> register(@RequestBody CusCustomerSaveDto cusCustomerSaveDto){

        CusCustomerDto cusCustomerDto =authenticationService.register(cusCustomerSaveDto);

        return ResponseEntity.ok(RestResponse.of(cusCustomerDto));
    }
}
