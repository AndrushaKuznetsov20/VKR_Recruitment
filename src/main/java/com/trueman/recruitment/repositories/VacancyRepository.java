package com.trueman.recruitment.repositories;

import com.trueman.recruitment.models.Resume;
import com.trueman.recruitment.models.User;
import com.trueman.recruitment.models.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Long>, JpaSpecificationExecutor<Vacancy> {
    @Query("SELECT v FROM Vacancy v WHERE v.user.id = :userId")
    List<Vacancy> findByUserId(@Param("userId") Long userId);
    List<Vacancy> findAll();

    @Query("SELECT v FROM Vacancy v WHERE v.status_vacancy = 'Опубликовано!'")
    List<Vacancy> findAllStatusOk();
    @Query("SELECT COUNT(v) FROM Vacancy v WHERE v.createDateTime BETWEEN :startDateTime AND :endDateTime AND v.user.id = :userId")
    int countVacancyInDateTimeRange(@Param("startDateTime")LocalDateTime startDateTime,
                                    @Param("endDateTime") LocalDateTime endDateTime,
                                    @Param("userId") Long userId);
}
