package com.cbarkinozer.onlinebankingrestapi.app.crd.service;

import com.cbarkinozer.onlinebankingrestapi.app.crd.dto.CrdCreditCardActivityAnalysisDto;
import com.cbarkinozer.onlinebankingrestapi.app.crd.dto.CrdCreditCardActivityDto;
import com.cbarkinozer.onlinebankingrestapi.app.crd.entity.CrdCreditCardActivity;
import com.cbarkinozer.onlinebankingrestapi.app.crd.mapper.CrdCreditCardMapper;
import com.cbarkinozer.onlinebankingrestapi.app.crd.service.entityservice.CrdCreditCardActivityEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class CrdCreditCardActivityService {

    private final CrdCreditCardActivityEntityService crdCreditCardActivityEntityService;
    private final CrdCreditCardActivityValidationService crdCreditCardActivityValidationService;



    public List<CrdCreditCardActivityDto> findCreditCardActivityByAmountInterval(BigDecimal min, BigDecimal max) {

        crdCreditCardActivityValidationService.controlIsParameterMinLargerThanMax(min,max);

        List<CrdCreditCardActivity> crdCreditCardActivityList = crdCreditCardActivityEntityService.findCreditCardActivityByAmountInterval(min,max);

        List<CrdCreditCardActivityDto> convertToCrdCreditCardDtoList = CrdCreditCardMapper.INSTANCE.convertToCrdCreditCardActivityDtoList(crdCreditCardActivityList);

        return convertToCrdCreditCardDtoList;
    }

    public List<CrdCreditCardActivityAnalysisDto> getCardActivityAnalysis(Long creditCardId) {

        crdCreditCardActivityValidationService.controlIsCreditCardExist(creditCardId);

        List<CrdCreditCardActivityAnalysisDto> crdCreditCardActivityAnalysisDtoList = crdCreditCardActivityEntityService.getCardActivityAnalysis(creditCardId);

        return crdCreditCardActivityAnalysisDtoList;
    }

}
