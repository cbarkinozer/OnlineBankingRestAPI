package com.cbarkinozer.onlinebankingrestapi.app.cus.service.entityservice;

import com.cbarkinozer.onlinebankingrestapi.app.cus.entity.CusCustomer;
import com.cbarkinozer.onlinebankingrestapi.app.cus.dao.CusCustomerDao;
import com.cbarkinozer.onlinebankingrestapi.app.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CusCustomerEntityService extends BaseEntityService<CusCustomer, CusCustomerDao> {

    public CusCustomerEntityService(CusCustomerDao cusCustomerDao) {
        super(cusCustomerDao);
    }

    public List<CusCustomer> findAllCustomers() {

        List<CusCustomer> cusCustomerList = getDao().findAll();

        return cusCustomerList;
    }

    public CusCustomer saveCustomer(CusCustomer cusCustomer) {

        cusCustomer = getDao().save(cusCustomer);

        return cusCustomer;
    }

    public Optional<CusCustomer> findByIdentityNo(Long id,Long identityNo) {

        Optional<CusCustomer> cusCustomerOptional = getDao().findByIdentityNo(id,identityNo);

        return cusCustomerOptional;
    }
}