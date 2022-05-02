package com.cbarkinozer.onlinebankingrestapi.app.loa.dao;

import com.cbarkinozer.onlinebankingrestapi.app.loa.entity.LoaLoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoaLoanDao extends JpaRepository<LoaLoan,Long> {
}
