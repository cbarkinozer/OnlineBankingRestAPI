package com.cbarkinozer.onlinebankingrestapi.app.crd.service.entityservice;

import com.cbarkinozer.onlinebankingrestapi.app.crd.dao.CrdCreditCardActivityDao;
import com.cbarkinozer.onlinebankingrestapi.app.crd.dto.CrdCreditCardActivityAnalysisDto;
import com.cbarkinozer.onlinebankingrestapi.app.crd.entity.CrdCreditCardActivity;
import com.cbarkinozer.onlinebankingrestapi.app.gen.service.BaseEntityService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CrdCreditCardActivityEntityService extends BaseEntityService<CrdCreditCardActivity, CrdCreditCardActivityDao> {

    public CrdCreditCardActivityEntityService(CrdCreditCardActivityDao dao) {
        super(dao);
    }

    public List<CrdCreditCardActivity> findCreditCardActivityByAmountInterval(BigDecimal min, BigDecimal max) {

        List<CrdCreditCardActivity> crdCreditCardActivityList = getDao().findAllByAmountBetween(min,max);

        return crdCreditCardActivityList;
    }

    public List<CrdCreditCardActivity> findCreditCardActivityBetweenDates(
            Long creditCardId,
            LocalDateTime startDateTime, LocalDateTime endDateTime) {

        return getDao().findAllByCrdCreditCardIdAndTransactionDateBetween(creditCardId, startDateTime, endDateTime);
    }

    public List<CrdCreditCardActivity> findCreditCardActivityBetweenDates(
            Long creditCardId,
            LocalDateTime startDateTime, LocalDateTime endDateTime,
            Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {

        PageRequest pageRequest = getPageRequest(pageOptional, sizeOptional);

        return getDao().findAllByCrdCreditCardIdAndTransactionDateBetween(creditCardId, startDateTime, endDateTime, pageRequest).toList();
    }

    public List<CrdCreditCardActivityAnalysisDto> getCardActivityAnalysis(Long creditCardId) {

        List<CrdCreditCardActivityAnalysisDto> crdCreditCardActivityAnalysisDtoList = getDao().getCardActivityAnalysis(creditCardId);

        return crdCreditCardActivityAnalysisDtoList;
    }

    public List<CrdCreditCardActivity> findAllByCrdCreditCardIdAndTransactionDateBetween(Long crdCreditCardId, LocalDateTime termStartDate, LocalDateTime termEndDate) {

        return getDao().findAllByCrdCreditCardIdAndTransactionDateBetween(crdCreditCardId,termStartDate,termEndDate);

    }
}
