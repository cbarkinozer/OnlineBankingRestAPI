package com.cbarkinozer.onlinebankingrestapi.app.acc.service;

import com.cbarkinozer.onlinebankingrestapi.app.acc.converter.AccAccountMapper;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccMoneyActivityDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccMoneyTransferDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccMoneyTransferSaveDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccMoneyTransfer;
import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccAccountActivityType;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.entityservice.AccMoneyTransferEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class AccMoneyTransferService {

    private final AccMoneyTransferEntityService accMoneyTransferEntityService;
    private final AccAccountActivityService accAccountActivityService;
    private final AccAccountValidationService accAccountValidationService;

    public AccMoneyTransferDto transferMoney(AccMoneyTransferSaveDto accMoneyTransferSaveDto) {

        AccMoneyTransfer accMoneyTransfer = AccAccountMapper.INSTANCE.convertToAccMoneyTransfer(accMoneyTransferSaveDto);
        LocalDate localDate = LocalDate.now();
        accMoneyTransfer.setTransferDate(localDate);

        Long accountIdFrom = accMoneyTransfer.getAccountIdFrom();
        Long accountIdTo = accMoneyTransfer.getAccountIdTo();
        BigDecimal amount = accMoneyTransfer.getAmount();

        accAccountValidationService.controlIsAccountIdExist(accountIdFrom);
        accAccountValidationService.controlIsAccountIdExist(accountIdTo);

        AccMoneyActivityDto accMoneyActivityDtoOut = AccMoneyActivityDto.builder()
                .accountId(accountIdFrom)
                .amount(amount)
                .activityType(AccAccountActivityType.SEND)
                .build();

        AccMoneyActivityDto accMoneyActivityDtoIn = AccMoneyActivityDto.builder()
                .accountId(accountIdTo)
                .amount(amount)
                .activityType(AccAccountActivityType.GET)
                .build();

        accAccountActivityService.moneyOut(accMoneyActivityDtoOut);
        accAccountActivityService.moneyIn(accMoneyActivityDtoIn);

        accMoneyTransfer = accMoneyTransferEntityService.save(accMoneyTransfer);
        AccMoneyTransferDto accMoneyTransferDto = AccAccountMapper.INSTANCE.convertToAccMoneyTransferDto(accMoneyTransfer);

        return accMoneyTransferDto;
    }
}
