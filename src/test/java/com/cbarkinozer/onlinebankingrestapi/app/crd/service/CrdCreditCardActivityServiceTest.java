package com.cbarkinozer.onlinebankingrestapi.app.crd.service;

import com.cbarkinozer.onlinebankingrestapi.app.crd.dto.CrdCreditCardActivityAnalysisDto;
import com.cbarkinozer.onlinebankingrestapi.app.crd.dto.CrdCreditCardActivityDto;
import com.cbarkinozer.onlinebankingrestapi.app.crd.entity.CrdCreditCard;
import com.cbarkinozer.onlinebankingrestapi.app.crd.entity.CrdCreditCardActivity;
import com.cbarkinozer.onlinebankingrestapi.app.crd.enums.CrdCreditCardActivityType;
import com.cbarkinozer.onlinebankingrestapi.app.crd.service.entityservice.CrdCreditCardActivityEntityService;
import com.cbarkinozer.onlinebankingrestapi.app.crd.service.entityservice.CrdCreditCardEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrdCreditCardActivityServiceTest {

    @Mock
    private CrdCreditCardActivityEntityService crdCreditCardActivityEntityService;

    @InjectMocks
    private CrdCreditCardActivityService crdCreditCardActivityService;

    @Test
    void shouldFindCreditCardActivityByAmountInterval() {

        BigDecimal min = BigDecimal.ONE;
        BigDecimal max = BigDecimal.valueOf(10000);

        List<CrdCreditCardActivity> crdCreditCardActivityList = createDummyCrdCreditCardActivityList();

        List<CrdCreditCardActivity> expectedResult = createDummyCrdCreditCardActivityList();

        when(crdCreditCardActivityEntityService.findCreditCardActivityByAmountInterval(min,max)).thenReturn(crdCreditCardActivityList);

        List<CrdCreditCardActivity> result = crdCreditCardActivityEntityService.findCreditCardActivityByAmountInterval(min,max);

        assertEquals(expectedResult, result);
        assertNotNull(result);
    }

    private List<CrdCreditCardActivity> createDummyCrdCreditCardActivityList() {

        List<CrdCreditCardActivity> crdCreditCardActivityList = new ArrayList<>();
        crdCreditCardActivityList.add(createDummyCrdCreditCardActivity());

        return crdCreditCardActivityList;
    }

    private CrdCreditCardActivity createDummyCrdCreditCardActivity() {

        CrdCreditCardActivity crdCreditCardActivity = mock(CrdCreditCardActivity.class);
        crdCreditCardActivity.setId(1L);
        crdCreditCardActivity.setCrdCreditCardId(1L);
        crdCreditCardActivity.setAmount(BigDecimal.valueOf(9000));
        crdCreditCardActivity.setTransactionDate(LocalDateTime.now());
        crdCreditCardActivity.setDescription("creditCardActivity");
        crdCreditCardActivity.setCardActivityType(CrdCreditCardActivityType.SPEND);

        return crdCreditCardActivity;
    }

    @Test
    void shouldGetCardActivityAnalysis() {

        List<CrdCreditCardActivityAnalysisDto> crdCreditCardActivityAnalysisDtoList = createDummyCrdCreditCardActivityAnalysisDtoList();

        List<CrdCreditCardActivityAnalysisDto> expectedResult = createDummyCrdCreditCardActivityAnalysisDtoList();

        when(crdCreditCardActivityEntityService.getCardActivityAnalysis(1L)).thenReturn(crdCreditCardActivityAnalysisDtoList);

        List<CrdCreditCardActivityAnalysisDto> result = crdCreditCardActivityEntityService.getCardActivityAnalysis(1L);

        assertEquals(expectedResult, result);
        assertNotNull(result);

    }

    private List<CrdCreditCardActivityAnalysisDto> createDummyCrdCreditCardActivityAnalysisDtoList() {

        List<CrdCreditCardActivityAnalysisDto> crdCreditCardActivityAnalysisDtoList = new ArrayList<>();
        crdCreditCardActivityAnalysisDtoList.add(createDummyCrdCreditCardActivityAnalysisDto());

        return crdCreditCardActivityAnalysisDtoList;
    }

    private CrdCreditCardActivityAnalysisDto createDummyCrdCreditCardActivityAnalysisDto() {

        CrdCreditCardActivityAnalysisDto crdCreditCardActivityAnalysisDto = mock(CrdCreditCardActivityAnalysisDto.class);

        crdCreditCardActivityAnalysisDto.setActivityType(CrdCreditCardActivityType.SPEND);
        crdCreditCardActivityAnalysisDto.setActivityCount(12L);
        crdCreditCardActivityAnalysisDto.setMinAmount(BigDecimal.valueOf(100));
        crdCreditCardActivityAnalysisDto.setMaxAmount(BigDecimal.valueOf(10000));
        crdCreditCardActivityAnalysisDto.setAvgAmount(505.0);

        return crdCreditCardActivityAnalysisDto;
    }
}