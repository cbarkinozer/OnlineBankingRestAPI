package com.cbarkinozer.onlinebankingrestapi.app.acc.service.entityservice;

import com.cbarkinozer.onlinebankingrestapi.app.acc.dao.AccAccountDao;
import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccAccount;
import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.GenStatusType;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.ItemNotFoundException;
import com.cbarkinozer.onlinebankingrestapi.app.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccAccountEntityService extends BaseEntityService<AccAccount, AccAccountDao> {

    public AccAccountEntityService(AccAccountDao accAccountDao){
        super(accAccountDao);
    }

    public List<AccAccount> findAllActiveAccounts() {

        List<AccAccount> accAccountList = getDao().findAllByStatusType(GenStatusType.ACTIVE);

        return accAccountList;
    }

    public List<AccAccount> findAccountByCustomerId(Long customerId) {

        List<AccAccount> accAccountList = getDao().findAllByCustomerId(customerId);

        if (accAccountList.isEmpty()){
            throw new ItemNotFoundException(AccErrorMessage.ACCOUNT_NOT_FOUND);
        }

        return accAccountList;
    }

    public Optional<AccAccount> findAccountByIbanNo(Long id, String ibanNo) {
        Optional<AccAccount> accountOptional = getDao().findByIbanNo(id,ibanNo);

        return accountOptional;
    }

    public Optional<AccAccount> findAccountById(Long id) {

        Optional<AccAccount> accountOptional = getDao().findById(id);

        return accountOptional;
    }
}
