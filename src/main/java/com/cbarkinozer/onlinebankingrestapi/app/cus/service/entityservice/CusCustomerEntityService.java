package com.cbarkinozer.onlinebankingrestapi.app.cus.service.entityservice;

import com.cbarkinozer.onlinebankingrestapi.app.cus.entity.CusCustomer;
import com.cbarkinozer.onlinebankingrestapi.app.cus.dao.CusCustomerDao;
import com.cbarkinozer.onlinebankingrestapi.app.cus.enums.CusErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.ItemNotFoundException;
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

    public CusCustomer findByIdentityNo(Long identityNo) {

        CusCustomer cusCustomer = getDao().findByIdentityNo(identityNo)
                .orElseThrow(()-> new ItemNotFoundException(CusErrorMessage.CUSTOMER_NOT_FOUND));

       return cusCustomer;
    }

    public Optional<CusCustomer> findByIdentityNo(CusCustomer cusCustomer) {

        Long identityNo = cusCustomer.getIdentityNo();

        Optional<CusCustomer> cusCustomerOptional = getDao().findByIdentityNo(identityNo);

        return cusCustomerOptional;
    }

    public CusCustomer findCustomerById(Long id){

        CusCustomer cusCustomer = super.findById(id)
                .orElseThrow(()-> new ItemNotFoundException(CusErrorMessage.CUSTOMER_NOT_FOUND));

        return cusCustomer;
    }


}
