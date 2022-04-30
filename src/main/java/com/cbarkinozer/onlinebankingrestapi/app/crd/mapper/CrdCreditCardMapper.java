package com.cbarkinozer.onlinebankingrestapi.app.crd.mapper;

import com.cbarkinozer.onlinebankingrestapi.app.crd.dto.CrdCreditCardActivityDto;
import com.cbarkinozer.onlinebankingrestapi.app.crd.dto.CrdCreditCardDto;
import com.cbarkinozer.onlinebankingrestapi.app.crd.entity.CrdCreditCard;
import com.cbarkinozer.onlinebankingrestapi.app.crd.entity.CrdCreditCardActivity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CrdCreditCardMapper {

    CrdCreditCardMapper INSTANCE = Mappers.getMapper(CrdCreditCardMapper.class);

    List<CrdCreditCardDto> convertToCrdCreditCardDtoList(List<CrdCreditCard> crdCreditCardList);

    List<CrdCreditCardActivityDto> convertToCrdCreditCardActivityDtoList(List<CrdCreditCardActivity> crdCreditCardActivityList);

    CrdCreditCardDto convertToCrdCreditCardDto(CrdCreditCard crdCreditCard);

    CrdCreditCardActivityDto convertToCrdCreditCardActivityDto(CrdCreditCardActivity crdCreditCardActivity);
}
