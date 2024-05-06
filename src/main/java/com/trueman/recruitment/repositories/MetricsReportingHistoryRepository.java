package com.trueman.recruitment.repositories;

import com.trueman.recruitment.models.MetricsReportingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface MetricsReportingHistoryRepository extends JpaRepository<MetricsReportingHistory, Long> {
    List<MetricsReportingHistory> findByUserId(@Param("userId") Long userId);
}
