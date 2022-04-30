package com.cbarkinozer.onlinebankingrestapi.app.crd.dao;

import com.cbarkinozer.onlinebankingrestapi.app.crd.dto.CrdCreditCardActivityAnalysisDto;
import com.cbarkinozer.onlinebankingrestapi.app.crd.entity.CrdCreditCardActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface CrdCreditCardActivityDao extends JpaRepository<CrdCreditCardActivity,Long> {

    List<CrdCreditCardActivity> findAllByAmountBetween(BigDecimal min, BigDecimal max);

    List<CrdCreditCardActivity> findAllByCrdCreditCardIdAndTransactionDateBetween(Long crdCreditCardId, LocalDateTime startDate, LocalDateTime endDate);

    Page<CrdCreditCardActivity> findAllByCrdCreditCardIdAndTransactionDateBetween(
            Long creditCardId,
            LocalDateTime startDateTime, LocalDateTime endDateTime,
            PageRequest pageRequest
    );

    @Query(value = "SELECT "+
            "new com.cbarkinozer.onlinebankingrestapi.app.crd.dto.CrdCreditCardActivityAnalysisDto( " +
            "activity.cardActivityType,"+
            "min(activity.amount),"+
            "max(activity.amount),"+
            "avg(activity.amount),"+
            "count(activity)" +
            " ) " +
            "FROM CrdCreditCard card "+
            "LEFT JOIN CrdCreditCardActivity activity "+
            "ON card.id = activity.crdCreditCardId " +
            "WHERE card.id = :creditCardId "+
            "GROUP BY activity.cardActivityType"
    )
    List<CrdCreditCardActivityAnalysisDto> getCardActivityAnalysis(@Param("creditCardId") Long creditCardId);
}
