package com.cbarkinozer.onlinebankingrestapi.app.crd.dao;

import com.cbarkinozer.onlinebankingrestapi.app.crd.entity.CrdCreditCard;
import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.GenStatusType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrdCreditCardDao extends JpaRepository<CrdCreditCard,Long> {

    List<CrdCreditCard> findAllByStatusType(GenStatusType statusType);


}
