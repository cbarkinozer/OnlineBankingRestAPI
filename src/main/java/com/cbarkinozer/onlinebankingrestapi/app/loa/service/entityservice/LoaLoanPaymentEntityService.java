package com.cbarkinozer.onlinebankingrestapi.app.loa.service.entityservice;

import com.cbarkinozer.onlinebankingrestapi.app.gen.service.BaseEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.loa.dao.LoaLoanDao;
import com.cbarkinozer.onlinebankingrestapi.app.loa.dao.LoaLoanPaymentDao;
import com.cbarkinozer.onlinebankingrestapi.app.loa.entity.LoaLoanPayment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoaLoanPaymentEntityService extends BaseEntityService<LoaLoanPayment, LoaLoanPaymentDao> {

    public LoaLoanPaymentEntityService(LoaLoanPaymentDao dao){
        super(dao);
    }
}
