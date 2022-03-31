package com.cbarkinozer.onlinebankingrestapi.app.cus.controller;

import com.cbarkinozer.onlinebankingrestapi.app.cus.dto.CusCustomerDto;
import com.cbarkinozer.onlinebankingrestapi.app.cus.dto.CusCustomerSaveDto;
import com.cbarkinozer.onlinebankingrestapi.app.cus.dto.CusCustomerUpdateDto;
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


    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<CusCustomerDto>> findCustomerById(@PathVariable Long id){

        CusCustomerDto cusCustomerDto = cusCustomerService.findCustomerById(id);

        return ResponseEntity.ok(RestResponse.of(cusCustomerDto));
    }


    @PostMapping("/save-customer")
    public ResponseEntity<RestResponse<CusCustomerDto>> saveCustomer(CusCustomerSaveDto cusCustomerSaveDto){

        CusCustomerDto cusCustomerDto = cusCustomerService.saveCustomer(cusCustomerSaveDto);

        return ResponseEntity.ok(RestResponse.of(cusCustomerDto));
    }

    @PutMapping("/update-customer")
    public ResponseEntity<RestResponse<CusCustomerDto>> updateCustomer(CusCustomerUpdateDto cusCustomerUpdateDto){

        CusCustomerDto cusCustomerDto = cusCustomerService.updateCustomer(cusCustomerUpdateDto);

        return ResponseEntity.ok(RestResponse.of(cusCustomerDto));
    }

    @DeleteMapping("delete-customer/{id}")
    public ResponseEntity<RestResponse<?>> deleteCustomer(@PathVariable Long id){

        cusCustomerService.deleteCustomer(id);

        return ResponseEntity.ok(RestResponse.empty());
    }
}
