package com.cbarkinozer.onlinebankingrestapi.app.acc.service;

import com.cbarkinozer.onlinebankingrestapi.app.acc.mapper.AccAccountMapper;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccAccountActivityDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccMoneyActivityDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccMoneyActivityRequestDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccAccount;
import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccAccountActivity;
import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccAccountActivityType;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.entityservice.AccAccountActivityEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.entityservice.AccAccountEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class AccAccountActivityService {

    private final AccAccountEntityService accAccountEntityService;
    private final AccAccountActivityEntityService accAccountActivityEntityService;
    private final AccAccountValidationService accAccountValidationService;

    public AccAccountActivity moneyOut(AccMoneyActivityDto accMoneyActivityDto) {

        accAccountValidationService.controlIsMoneyActivityDtoNotNull(accMoneyActivityDto);

        Long accountId = accMoneyActivityDto.getAccountId();
        BigDecimal amount = accMoneyActivityDto.getAmount();
        AccAccountActivityType activityType = accMoneyActivityDto.getActivityType();

        AccAccount accAccount = accAccountEntityService.getByIdWithControl(accountId);
        BigDecimal newBalance = accAccount.getCurrentBalance().subtract(amount);

        accAccountValidationService.controlIsAmountPositive(amount);
        accAccountValidationService.controlIsBalanceNotNegative(newBalance);

        AccAccountActivity accAccountActivity = createAccAccountActivity(accountId, amount, newBalance, activityType);

        updateCurrentBalance(accAccount, newBalance);

        return accAccountActivity;
    }

    public AccAccountActivity moneyIn(AccMoneyActivityDto accMoneyActivityDto) {

        accAccountValidationService.controlIsMoneyActivityDtoNotNull(accMoneyActivityDto);

        Long accountId = accMoneyActivityDto.getAccountId();
        BigDecimal amount = accMoneyActivityDto.getAmount();
        AccAccountActivityType activityType = accMoneyActivityDto.getActivityType();

        AccAccount accAccount = accAccountEntityService.getByIdWithControl(accountId);
        BigDecimal newBalance = accAccount.getCurrentBalance().add(amount);

        accAccountValidationService.controlIsAmountPositive(amount);
        accAccountValidationService.controlIsBalanceNotNegative(newBalance);

        AccAccountActivity accAccountActivity = createAccAccountActivity(accountId, amount, newBalance, activityType);

        updateCurrentBalance(accAccount, newBalance);

        return accAccountActivity;
    }

    private void updateCurrentBalance(AccAccount accAccount, BigDecimal newBalance) {

        accAccount.setCurrentBalance(newBalance);
        accAccountEntityService.save(accAccount);
    }

    private AccAccountActivity createAccAccountActivity(Long accountId, BigDecimal amount, BigDecimal newBalance, AccAccountActivityType accAccountActivityType) {

        AccAccountActivity accAccountActivity = new AccAccountActivity();
        accAccountActivity.setAccountActivityType(accAccountActivityType);
        accAccountActivity.setAccountId(accountId);
        accAccountActivity.setAmount(amount);

        LocalDate localDate = LocalDate.now();
        accAccountActivity.setTransactionDate(localDate);

        accAccountActivity.setCurrentBalance(newBalance);
        accAccountActivity = accAccountActivityEntityService.save(accAccountActivity);

        return accAccountActivity;
    }



    public AccAccountActivityDto withdraw(AccMoneyActivityRequestDto accMoneyActivityRequestDto) {

        accAccountValidationService.controlIsMoneyActivityRequestDtoNotNull(accMoneyActivityRequestDto);

        Long accAccountId = accMoneyActivityRequestDto.getAccountId();
        BigDecimal amount = accMoneyActivityRequestDto.getAmount();

        accAccountValidationService.controlIsAccountIdExist(accAccountId);
        accAccountValidationService.controlIsAmountPositive(amount);

        AccMoneyActivityDto accMoneyActivityDto = AccMoneyActivityDto.builder()
                .accountId(accAccountId)
                .amount(amount)
                .activityType(AccAccountActivityType.WITHDRAW)
                .build();

        AccAccountActivity accAccountActivity = moneyOut(accMoneyActivityDto);

        AccAccountActivityDto accAccountActivityDto = AccAccountMapper.INSTANCE.convertToAccAccountActivityDto(accAccountActivity);

        return accAccountActivityDto;
    }

    public AccAccountActivityDto deposit(AccMoneyActivityRequestDto accMoneyActivityRequestDto) {

        accAccountValidationService.controlIsMoneyActivityRequestDtoNotNull(accMoneyActivityRequestDto);

        Long accAccountId = accMoneyActivityRequestDto.getAccountId();
        BigDecimal amount = accMoneyActivityRequestDto.getAmount();

        accAccountValidationService.controlIsAccountIdExist(accAccountId);
        accAccountValidationService.controlIsAmountPositive(amount);

        AccMoneyActivityDto accMoneyActivityDto = AccMoneyActivityDto.builder()
                .accountId(accAccountId)
                .amount(amount)
                .activityType(AccAccountActivityType.DEPOSIT)
                .build();

        AccAccountActivity accAccountActivity = moneyIn(accMoneyActivityDto);

        AccAccountActivityDto accAccountActivityDto = AccAccountMapper.INSTANCE.convertToAccAccountActivityDto(accAccountActivity);

        return accAccountActivityDto;
    }
}
