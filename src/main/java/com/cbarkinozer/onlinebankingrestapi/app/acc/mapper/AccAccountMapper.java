package com.cbarkinozer.onlinebankingrestapi.app.acc.mapper;

import com.cbarkinozer.onlinebankingrestapi.app.acc.dto.*;
import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccAccount;
import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccAccountActivity;
import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccMoneyTransfer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccAccountMapper {

    AccAccountMapper INSTANCE = Mappers.getMapper(AccAccountMapper.class);

    AccAccountDto convertToAccAccountDto(AccAccount accAccount);

    List<AccAccountDto> convertToAccAccountDtoList(List<AccAccount> accAccountList);

    AccAccount convertToAccAccount(AccAccountSaveDto accAccountSaveDto);

    AccMoneyTransfer convertToAccMoneyTransfer(AccMoneyTransferSaveDto accMoneyTransferSaveDto);

    AccMoneyTransferDto convertToAccMoneyTransferDto(AccMoneyTransfer accMoneyTransfer);

    AccAccountActivityDto convertToAccAccountActivityDto(AccAccountActivity accAccountActivity);

}
