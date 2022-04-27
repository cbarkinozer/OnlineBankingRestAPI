package com.cbarkinozer.onlinebankingrestapi.app.cus.dao;

import com.cbarkinozer.onlinebankingrestapi.app.cus.entity.CusCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CusCustomerDao extends JpaRepository<CusCustomer,Long> {

    Optional<CusCustomer> findByIdentityNo(Long identityNo);
}
