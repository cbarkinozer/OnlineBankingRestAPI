package com.cbarkinozer.onlinebankingrestapi.app.acc.service.entityservice;

import com.cbarkinozer.onlinebankingrestapi.app.acc.dao.AccAccountActivityDao;
import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccAccountActivity;
import com.cbarkinozer.onlinebankingrestapi.app.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccAccountActivityEntityService extends BaseEntityService<AccAccountActivity, AccAccountActivityDao> {
    public AccAccountActivityEntityService(AccAccountActivityDao accAccountActivityDao){
        super(accAccountActivityDao);
    }
}
