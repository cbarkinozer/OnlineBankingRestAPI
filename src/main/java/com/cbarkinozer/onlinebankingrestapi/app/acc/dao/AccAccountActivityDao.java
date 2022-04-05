package com.cbarkinozer.onlinebankingrestapi.app.acc.dao;

import com.cbarkinozer.onlinebankingrestapi.app.acc.entity.AccAccountActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccAccountActivityDao extends JpaRepository<AccAccountActivity,Long> {
}
