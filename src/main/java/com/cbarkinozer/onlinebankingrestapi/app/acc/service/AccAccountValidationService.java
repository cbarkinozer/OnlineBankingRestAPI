package com.cbarkinozer.onlinebankingrestapi.app.acc.service;

import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccAccount;
import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.entityservice.AccAccountEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.IllegalFieldException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccAccountValidationService {

    private final AccAccountEntityService accAccountEntityService;

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

}
