package com.cbarkinozer.onlinebankingrestapi.app.cus.dao;

import com.cbarkinozer.onlinebankingrestapi.app.cus.entity.CusCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CusCustomerDao extends JpaRepository<CusCustomer,Long> {

    @Query(
            "SELECT " +
                    "customer "+
                    "FROM CusCustomer customer "+
                    "WHERE customer.identityNo = :identityNo "+
                    "AND customer.id <> :id "
    )
    Optional<CusCustomer> findByIdentityNo(@Param("id") Long id ,@Param("identityNo") Long identityNo);
}
