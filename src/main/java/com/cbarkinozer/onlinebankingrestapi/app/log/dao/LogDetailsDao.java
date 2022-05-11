package com.cbarkinozer.onlinebankingrestapi.app.log.dao;

import com.cbarkinozer.onlinebankingrestapi.app.log.entity.LogDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDetailsDao extends JpaRepository<LogDetail, Long> {
}
