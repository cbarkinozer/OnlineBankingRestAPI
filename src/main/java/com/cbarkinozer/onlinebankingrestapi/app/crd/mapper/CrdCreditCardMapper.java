package com.cbarkinozer.onlinebankingrestapi.app.crd.mapper;

import com.cbarkinozer.onlinebankingrestapi.app.crd.dto.CrdCreditCardDto;
import com.cbarkinozer.onlinebankingrestapi.app.crd.entity.CrdCreditCard;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CrdCreditCardMapper {

    CrdCreditCardMapper INSTANCE = Mappers.getMapper(CrdCreditCardMapper.class);

    List<CrdCreditCardDto> convertToCrdCreditCardDtoList(List<CrdCreditCard> crdCreditCardList);

}
