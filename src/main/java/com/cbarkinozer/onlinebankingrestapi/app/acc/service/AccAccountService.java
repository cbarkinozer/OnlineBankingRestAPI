package com.cbarkinozer.onlinebankingrestapi.app.acc.service;

import com.cbarkinozer.onlinebankingrestapi.app.acc.converter.AccAccountMapper;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccAccountDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.AccAccountSaveDto;
import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccAccount;
import com.cbarkinozer.onlinebankingrestapi.app.acc.service.entityservice.AccAccountEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.GenStatusType;
import com.cbarkinozer.onlinebankingrestapi.app.gen.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccAccountService {

    private final AccAccountEntityService accAccountEntityService;
    private final AccAccountValidationService accAccountValidationService;

    public List<AccAccountDto> findAllAccounts() {

        List<AccAccount> accAccountList = accAccountEntityService.findAllActiveAccounts();

        List<AccAccountDto> accAccountDtoList = AccAccountMapper.INSTANCE.convertToAccAccountDtoList(accAccountList);

        return accAccountDtoList;
    }

    public AccAccountDto findAccountById(Long id) {

        AccAccount accAccount = accAccountEntityService.getByIdWithControl(id);

        AccAccountDto accAccountDto = AccAccountMapper.INSTANCE.convertToAccAccountDto(accAccount);

        return accAccountDto;
    }

    public AccAccountDto findAccountByCustomerId(Long customerId) {

        AccAccount accAccount = accAccountEntityService.findAccountByCustomerId(customerId);

        AccAccountDto accAccountDto = AccAccountMapper.INSTANCE.convertToAccAccountDto(accAccount);

        return accAccountDto;
    }

    public AccAccountDto saveAccount(AccAccountSaveDto accAccountSaveDto) {

        String ibanNo = getIbanNo();

        Long currentCustomerId = accAccountEntityService.getCurrentCustomerId();

        AccAccount accAccount = AccAccountMapper.INSTANCE.convertToAccAccount(accAccountSaveDto);

        accAccountValidationService.controlIsIbanNoUnique(accAccount);

        accAccount.setStatusType(GenStatusType.ACTIVE);
        accAccount.setIbanNo(ibanNo);
        accAccount.setCustomerId(currentCustomerId);

        accAccount = accAccountEntityService.save(accAccount);

        AccAccountDto accAccountDto = AccAccountMapper.INSTANCE.convertToAccAccountDto(accAccount);

        return accAccountDto;
    }

    public void cancelAccount(Long id) {

        AccAccount accAccount = accAccountEntityService.getByIdWithControl(id);

        accAccount.setStatusType(GenStatusType.PASSIVE);
        accAccountEntityService.save(accAccount);
    }

    private String getIbanNo() {
        String ibanNo = StringUtil.getRandomNumberAsString(26);
        return ibanNo;
    }
}
