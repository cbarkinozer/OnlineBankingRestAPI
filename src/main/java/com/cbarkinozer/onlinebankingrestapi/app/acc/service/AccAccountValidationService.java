package com.cbarkinozer.onlinebankingrestapi.app.acc.service;

import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccMoneyActivityDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccMoneyActivityRequestDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccMoneyTransferSaveDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccAccount;
import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.entityservice.AccAccountEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.cus.entity.CusCustomer;
import com.cbarkinozer.onlinebankingrestapi.app.cus.enums.CusErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.cus.service.entityservice.CusCustomerEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.GenErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.GenBusinessException;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.IllegalFieldException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccAccountValidationService {

    private final AccAccountEntityService accAccountEntityService;
    private final CusCustomerEntityService cusCustomerEntityService;

    public void controlIsIbanNoUnique(AccAccount accAccount) {

        Long id = accAccount.getId();
        String ibanNo = accAccount.getIbanNo();

        Optional<AccAccount> accountOptional =
                accAccountEntityService.findAccountByIbanNo(id,ibanNo);

        if(accountOptional.isPresent()){
            throw new IllegalFieldException(AccErrorMessage.IBAN_NO_IS_NOT_UNIQUE);
        }

    }

    public void controlIsAccountIdExist(Long accountId) {
        Optional<AccAccount> accountOptional = accAccountEntityService.findAccountById(accountId);
        if(accountOptional.isEmpty()){
            throw new IllegalFieldException(AccErrorMessage.ACCOUNT_NOT_FOUND);
        }
    }

    public void controlIsCustomerExist(Long currentCustomerId) {
        CusCustomer cusCustomer = cusCustomerEntityService.findCustomerById(currentCustomerId);
        if(cusCustomer == null){
            throw new IllegalFieldException(CusErrorMessage.CUSTOMER_NOT_FOUND);
        }
    }

    public void controlAreFieldsNotNull(AccAccount accAccount) {
        boolean hasNullField =
                        accAccount.getCustomerId() == null ||
                        accAccount.getIbanNo().isBlank() ||
                        accAccount.getCurrentBalance() == null ||
                        accAccount.getCurrencyType() == null ||
                        accAccount.getAccountType() == null ||
                        accAccount.getStatusType() == null ;
        if(hasNullField){
            throw new IllegalFieldException(AccErrorMessage.FIELD_CANNOT_BE_NULL);
        }
    }

    public void controlIsBalanceNotNegative(AccAccount accAccount) {

        if(accAccount.getCurrentBalance().compareTo(BigDecimal.ZERO) < 0 ){

            throw new IllegalFieldException(AccErrorMessage.BALANCE_CANNOT_BE_NEGATIVE);
        }
    }

    public void controlIsBalanceNotNegative(BigDecimal newBalance) {

        if (newBalance.compareTo(BigDecimal.ZERO) < 0){

            throw new GenBusinessException(AccErrorMessage.INSUFFICIENT_BALANCE);
        }
    }

    public void controlIsMoneyActivityRequestDtoNotNull(AccMoneyActivityRequestDto accMoneyActivityRequestDto) {
        if (accMoneyActivityRequestDto == null){
            throw new GenBusinessException(GenErrorMessage.PARAMETER_CANNOT_BE_NULL);
        }
    }

    public void controlIsMoneyActivityDtoNotNull(AccMoneyActivityDto accMoneyActivityDto) {

        if (accMoneyActivityDto == null){
            throw new GenBusinessException(GenErrorMessage.PARAMETER_CANNOT_BE_NULL);
        }
    }

    public void controlIsMoneyTransferSaveDtoIsNull(AccMoneyTransferSaveDto accMoneyTransferSaveDto) {

        if (accMoneyTransferSaveDto == null){
            throw new GenBusinessException(GenErrorMessage.PARAMETER_CANNOT_BE_NULL);
        }
    }

    public void controlIsAmountPositive(BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) < 1){

            throw new IllegalFieldException(AccErrorMessage.AMOUNT_MUST_BE_POSITIVE);
        }
    }
}
