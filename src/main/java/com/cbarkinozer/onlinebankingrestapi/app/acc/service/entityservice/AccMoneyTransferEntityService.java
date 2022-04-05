package com.cbarkinozer.onlinebankingrestapi.app.acc.service.entityservice;

import com.cbarkinozer.onlinebankingrestapi.app.acc.dao.AccMoneyTransferDao;
import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccMoneyTransfer;
import com.cbarkinozer.onlinebankingrestapi.app.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccMoneyTransferEntityService extends BaseEntityService<AccMoneyTransfer, AccMoneyTransferDao> {
    public AccMoneyTransferEntityService(AccMoneyTransferDao accMoneyTransferDao){
        super(accMoneyTransferDao);
    }
}
