package com.trueman.recruitment.repositories;

import com.trueman.recruitment.models.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findByVacancyId(@Param("vacancyId") Long vacancyId);
    List<Response> findByUserId(@Param("userId") Long userId);
    Response findByUserIdAndVacancyId(@Param("userId") Long userId, @Param("vacancyId") Long vacancyId);

    @Query("SELECT COUNT(r) FROM Response r WHERE r.currentDateTime BETWEEN :startDateTime AND :endDateTime AND r.vacancy.id =:vacancyId")
    int countResponses(@Param("startDateTime") LocalDateTime startDateTime,
                       @Param("endDateTime") LocalDateTime endDateTime,
                       @Param("vacancyId") Long vacancyId);

    @Query("SELECT r FROM Response r WHERE r.currentDateTime BETWEEN :startDateTime AND :endDateTime")
    int findAll(@Param("startDateTime") LocalDateTime startDateTime,
                           @Param("endDateTime") LocalDateTime endDateTime);
}
