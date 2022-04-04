package com.cbarkinozer.onlinebankingrestapi.app.acc.service.entityservice;

import com.cbarkinozer.onlinebankingrestapi.app.acc.dao.AccAccountDao;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccAccountDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccAccount;
import com.cbarkinozer.onlinebankingrestapi.app.acc.enums.AccErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.GenErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.GenStatusType;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.ItemNotFoundException;
import com.cbarkinozer.onlinebankingrestapi.app.gen.service.BaseEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public List<AccAccount> findAllActiveAccounts(Optional<Integer> pageOptional,
                                                  Optional<Integer> sizeOptional) {

        PageRequest pageRequest = getPageRequest(pageOptional, sizeOptional);

        Page<AccAccount> accAccountPage = getDao().findAllByStatusType(GenStatusType.ACTIVE, pageRequest);

        return accAccountPage.toList();
    }

    public AccAccount findAccountByCustomerId(Long customerId) {

        Optional<AccAccount> accountOptional = getDao().findByCustomerId(customerId);

        AccAccount accAccount;
        if (accountOptional.isPresent()){
            accAccount = accountOptional.get();
        } else {
            throw new ItemNotFoundException(AccErrorMessage.ACCOUNT_NOT_FOUND);
        }

        return accAccount;
    }

}
