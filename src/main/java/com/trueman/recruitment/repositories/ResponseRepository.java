package com.trueman.recruitment.repositories;

import com.trueman.recruitment.models.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findByVacancyId(@Param("vacancyId") Long vacancyId);
    void deleteByVacancyId(Long vacancyId);
    Page<Response> findByUserId(@Param("userId") Long userId, Pageable pageable);
    Response findByUserIdAndVacancyId(@Param("userId") Long userId, @Param("vacancyId") Long vacancyId);

    @Query("SELECT COUNT(r) FROM Response r WHERE r.currentDate >= :startDate AND r.currentDate <= :endDate AND r.vacancy.id =:vacancyId")
    int countResponses(@Param("startDate") LocalDate startDate,
                       @Param("endDate") LocalDate endDate,
                       @Param("vacancyId") Long vacancyId);

    @Query("SELECT COUNT(r) FROM Response r WHERE r.vacancy.id =:vacancyId")
    int vacancyResponseCount(@Param("vacancyId") Long vacancyId);

    @Query("SELECT COUNT(r) FROM Response r WHERE r.currentDate >= :startDate AND r.currentDate <= :endDate AND r.vacancy.id =:vacancyId AND r.statusResponse = 'Самоотказ!'")
    int countSelfDanial(@Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("vacancyId") Long vacancyId);

    @Query("SELECT COUNT(r) FROM Response r WHERE r.vacancy.id =:vacancyId AND r.statusResponse = 'Самоотказ!'")
    int vacancySelfDanialCount(@Param("vacancyId") Long vacancyId);

    @Query("SELECT COUNT(r) FROM Response r WHERE r.currentDate >= :startDate AND r.currentDate <= :endDate AND r.vacancy.id =:vacancyId AND r.statusResponse = 'Отказ работодателя!'")
    int countRefusalEmployer(@Param("startDate") LocalDate startDate,
                             @Param("endDate") LocalDate endDate,
                             @Param("vacancyId") Long vacancyId);

    @Query("SELECT COUNT(r) FROM Response r WHERE r.vacancy.id =:vacancyId AND r.statusResponse = 'Отказ работодателя!'")
    int vacancyRefusalEmployerCount(@Param("vacancyId") Long vacancyId);

    @Query("SELECT COUNT(r) FROM Response r WHERE r.currentDate >= :startDate AND r.currentDate <= :endDate AND r.vacancy.id =:vacancyId AND r.statusResponse = 'Релевантный отклик!'")
    int countRelevantResponses(@Param("startDate") LocalDate startDate,
                               @Param("endDate") LocalDate endDate,
                               @Param("vacancyId") Long vacancyId);

    @Query("SELECT COUNT(r) FROM Response r WHERE r.vacancy.id =:vacancyId AND r.statusResponse = 'Релевантный отклик!'")
    int vacancyRelevantResponsesCount(@Param("vacancyId") Long vacancyId);

    @Query("SELECT COUNT(r) FROM Response r WHERE r.currentDate >= :startDate AND r.currentDate <= :endDate AND r.vacancy.id =:vacancyId AND r.statusResponse = 'Кандидат приглашён!'")
    int сountInvitation(@Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("vacancyId") Long vacancyId);

    @Query("SELECT COUNT(r) FROM Response r WHERE r.vacancy.id =:vacancyId AND r.statusResponse = 'Кандидат приглашён!'")
    int vacancyInvitationCount(@Param("vacancyId") Long vacancyId);
}
