package com.cbarkinozer.onlinebankingrestapi.app.cus.service;

import com.cbarkinozer.onlinebankingrestapi.app.cus.entity.CusCustomer;
import com.cbarkinozer.onlinebankingrestapi.app.cus.enums.CusErrorMessage;
import com.cbarkinozer.onlinebankingrestapi.app.cus.service.entityservice.CusCustomerEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.IllegalFieldException;
import com.cbarkinozer.onlinebankingrestapi.app.gen.exceptions.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CusCustomerValidationService {

    private final CusCustomerEntityService cusCustomerEntityService;

    public void controlIsCustomerExist(Long id) {

        boolean isExist = cusCustomerEntityService.existsById(id);

        if (!isExist){

            throw new ItemNotFoundException(CusErrorMessage.CUSTOMER_NOT_FOUND);
        }
        
    }

    public void controlAreFieldsNonNull(CusCustomer cusCustomer) {
        boolean hasNullField =
                cusCustomer.getName().isBlank()             ||
                        cusCustomer.getSurname().isBlank()  ||
                        cusCustomer.getIdentityNo() == null ||
                        cusCustomer.getPassword().isBlank();

        if(hasNullField){

            throw new IllegalFieldException(CusErrorMessage.FIELD_CANNOT_BE_NULL);
        }
    }

    public void controlIsIdentityNoUnique(CusCustomer cusCustomer) {

        Optional<CusCustomer> cusCustomerOptional = cusCustomerEntityService.findByIdentityNo(cusCustomer);

        CusCustomer cusCustomerReturned;
        if(cusCustomerOptional.isPresent()){

            cusCustomerReturned = cusCustomerOptional.get();

            boolean didMatchedItself = didMatchedItself(cusCustomerReturned, cusCustomer);

            if(!didMatchedItself){
                throw new IllegalFieldException(CusErrorMessage.IDENTITY_NO_MUST_BE_UNIQUE);
            }

        }

    }

    private Boolean didMatchedItself(CusCustomer usrUserReturned, CusCustomer cusCustomer){

        return usrUserReturned.getId().equals(cusCustomer.getId());
    }

}
