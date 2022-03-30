package com.cbarkinozer.onlinebankingrestapi.app.cus.service.entityservice;

import com.cbarkinozer.onlinebankingrestapi.app.cus.entity.CusCustomer;
import com.cbarkinozer.onlinebankingrestapi.app.cus.dao.CusCustomerDao;
import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.GenStatusType;
import com.cbarkinozer.onlinebankingrestapi.app.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CusCustomerEntityService extends BaseEntityService<CusCustomer, CusCustomerDao> {

    public CusCustomerEntityService(CusCustomerDao cusCustomerDao) {
        super(cusCustomerDao);
    }

    public List<CusCustomer> findAllCustomers() {

        List<CusCustomer> cusCustomerList = getDao().findAllByStatusType(GenStatusType.ACTIVE);

        return cusCustomerList;
    }
}
