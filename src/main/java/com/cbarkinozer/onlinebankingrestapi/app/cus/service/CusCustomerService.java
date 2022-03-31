package com.cbarkinozer.onlinebankingrestapi.app.cus.service;

import com.cbarkinozer.onlinebankingrestapi.app.cus.converter.CusCustomerMapper;
import com.cbarkinozer.onlinebankingrestapi.app.cus.dto.CusCustomerDto;
import com.cbarkinozer.onlinebankingrestapi.app.cus.dto.CusCustomerSaveDto;
import com.cbarkinozer.onlinebankingrestapi.app.cus.dto.CusCustomerUpdateDto;
import com.cbarkinozer.onlinebankingrestapi.app.cus.entity.CusCustomer;
import com.cbarkinozer.onlinebankingrestapi.app.cus.service.entityservice.CusCustomerEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CusCustomerService {

    public final CusCustomerEntityService cusCustomerEntityService;
    public final CusCustomerValidationService cusCustomerValidationService;

    public List<CusCustomerDto> findAllCustomers(){

        List<CusCustomer> cusCustomerList = cusCustomerEntityService.findAllCustomers();

        List<CusCustomerDto> cusCustomerDtoList = CusCustomerMapper.INSTANCE.convertToCusCustomerDtoList(cusCustomerList);

        return cusCustomerDtoList;
    }

    public CusCustomerDto findCustomerById(Long id) {

        CusCustomer cusCustomer = cusCustomerEntityService.getByIdWithControl(id);

        CusCustomerDto cusCustomerDto = CusCustomerMapper.INSTANCE.convertToCusCustomerDto(cusCustomer);

        return cusCustomerDto;
    }

    public CusCustomerDto saveCustomer(CusCustomerSaveDto cusCustomerSaveDto) {

        CusCustomer cusCustomer = CusCustomerMapper.INSTANCE.convertToCusCustomer(cusCustomerSaveDto);

        cusCustomer = cusCustomerEntityService.saveCustomer(cusCustomer);

        CusCustomerDto cusCustomerDto = CusCustomerMapper.INSTANCE.convertToCusCustomerDto(cusCustomer);

        return cusCustomerDto;
    }

    public CusCustomerDto updateCustomer(CusCustomerUpdateDto cusCustomerUpdateDto) {

        CusCustomer cusCustomer = CusCustomerMapper.INSTANCE.convertToCusCustomer(cusCustomerUpdateDto);

        cusCustomer = cusCustomerEntityService.saveCustomer(cusCustomer);

        CusCustomerDto cusCustomerDto = CusCustomerMapper.INSTANCE.convertToCusCustomerDto(cusCustomer);

        return cusCustomerDto;
    }

    public void deleteCustomer(Long id) {

        CusCustomer cusCustomer = cusCustomerEntityService.getByIdWithControl(id);

        cusCustomerEntityService.delete(cusCustomer);
    }
}
