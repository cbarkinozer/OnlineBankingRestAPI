package com.cbarkinozer.onlinebankingrestapi.app.loa.service.entityservice;

import com.cbarkinozer.onlinebankingrestapi.app.loa.dao.LoaLoanDao;
import com.cbarkinozer.onlinebankingrestapi.app.loa.entity.LoaLoan;
import com.cbarkinozer.onlinebankingrestapi.app.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoaLoanEntityService extends BaseEntityService<LoaLoan, LoaLoanDao> {

    public LoaLoanEntityService(LoaLoanDao dao){
        super(dao);
    }

}
