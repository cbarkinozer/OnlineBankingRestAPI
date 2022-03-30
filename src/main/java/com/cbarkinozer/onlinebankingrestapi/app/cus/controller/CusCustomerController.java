package com.cbarkinozer.onlinebankingrestapi.app.cus.controller;

import com.cbarkinozer.onlinebankingrestapi.app.cus.dto.CusCustomerDto;
import com.cbarkinozer.onlinebankingrestapi.app.cus.entity.CusCustomer;
import com.cbarkinozer.onlinebankingrestapi.app.cus.service.CusCustomerService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.dto.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customers")
public class CusCustomerController {

    private final CusCustomerService cusCustomerService;

    @GetMapping
    public ResponseEntity<RestResponse<List<CusCustomerDto>>> findAllCustomers(){

        List<CusCustomerDto> cusCustomerDtoList = cusCustomerService.findAllCustomers();
        return ResponseEntity.ok(RestResponse.of(cusCustomerDtoList));
    }

    /*
    @GetMapping
    public ResponseEntity findCustomerByd(){

    }

    @PostMapping
    public ResponseEntity saveCustomer(){

    }

    @PutMapping
    public ResponseEntity updateCustomer(){

    }

    @PatchMapping
    public ResponseEntity cancelCustomer(){

    }

     */
}
