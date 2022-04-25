package com.cbarkinozer.onlinebankingrestapi.app.acc.dao;

import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccAccount;
import com.cbarkinozer.onlinebankingrestapi.app.gen.enums.GenStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccAccountDao extends JpaRepository<AccAccount,Long> {

    List<AccAccount> findAllByStatusType(GenStatusType statusType);

    List<AccAccount> findAllByCustomerId(Long customerId);

    @Query(
            "SELECT " +
                    "account "+
                    "FROM AccAccount account "+
                    "WHERE account.ibanNo = :ibanNo "+
                    "AND account.id <> :id "
    )
    Optional<AccAccount> findByIbanNo(@Param("id") Long id, @Param("ibanNo") String ibanNo);

}
