package com.cbarkinozer.onlinebankingrestapi.app.acc.dao;

import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccAccount;
import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.GenStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccAccountDao extends JpaRepository<AccAccount,Long> {

    List<AccAccount> findAllByStatusType(GenStatusType statusType);

    Page<AccAccount> findAllByStatusType(GenStatusType statusType, Pageable pageable);

    Optional<AccAccount> findByCustomerId(Long customerId);
}
