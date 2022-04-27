package com.cbarkinozer.onlinebankingrestapi.app.crd.dao;

import com.cbarkinozer.onlinebankingrestapi.app.crd.entity.CrdCreditCard;
import com.cbarkinozer.onlinebankingrestapi.app.crd.entity.CrdCreditCardActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CrdCreditCardActivityDao extends JpaRepository<CrdCreditCardActivity,Long> {

    List<CrdCreditCardActivity> findAllByAmountBetween(BigDecimal min, BigDecimal max);
}
