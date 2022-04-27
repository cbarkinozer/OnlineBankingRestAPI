package com.cbarkinozer.onlinebankingrestapi.app.crd.dao;

import com.cbarkinozer.onlinebankingrestapi.app.crd.entity.CrdCreditCard;
import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.GenStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrdCreditCardDao extends JpaRepository<CrdCreditCard,Long> {

    List<CrdCreditCard> findAllByStatusType(GenStatusType statusType);


}
