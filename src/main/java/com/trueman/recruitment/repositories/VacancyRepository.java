package com.trueman.recruitment.repositories;

import com.trueman.recruitment.models.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Long>, JpaSpecificationExecutor<Vacancy> {
    @Query("SELECT v FROM Vacancy v WHERE v.user.id = :userId")
    Page<Vacancy> findByUserId(@Param("userId") Long userId, Pageable pageable);
    List<Vacancy> findAll();

    @Query("SELECT v FROM Vacancy v WHERE v.user.id = :userId")
    List<Vacancy> findByUserId(@Param("userId") Long userId);

    @Query("SELECT v FROM Vacancy v WHERE v.status_vacancy = 'Опубликована!'")
    Page<Vacancy> findAllStatusOk(Pageable pageable);
    @Query("SELECT COUNT(v) FROM Vacancy v WHERE v.createDateTime >= :startDate AND v.createDateTime <= :endDate AND v.user.id = :userId")
    int countVacancyInDateTimeRange(@Param("startDate") LocalDate startDate,
                                    @Param("endDate") LocalDate endDate,
                                    @Param("userId") Long userId);

//    @Query("SELECT v FROM Vacancy v WHERE v.status_vacancy = 'Опубликована!'")
//    List<Vacancy> searchAllVacancyStatusOk(Specification specification);
}
