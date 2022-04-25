package com.cbarkinozer.onlinebankingrestapi.app.crd.service;

import com.cbarkinozer.onlinebankingrestapi.app.crd.dto.CrdCreditCardDto;
import com.cbarkinozer.onlinebankingrestapi.app.crd.entity.CrdCreditCard;
import com.cbarkinozer.onlinebankingrestapi.app.crd.mapper.CrdCreditCardMapper;
import com.cbarkinozer.onlinebankingrestapi.app.crd.service.entityservice.CrdCreditCardEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CrdCreditCardService {

    private final CrdCreditCardEntityService crdCreditCardEntityService;

    public List<CrdCreditCardDto> findAllCreditCards() {

        List<CrdCreditCard> crdCreditCardList = crdCreditCardEntityService.findAllActiveCreditCardList();

        List<CrdCreditCardDto> crdCreditCardResponseDtoList = CrdCreditCardMapper.INSTANCE.convertToCrdCreditCardDtoList(crdCreditCardList);

        return crdCreditCardResponseDtoList;
    }
}
