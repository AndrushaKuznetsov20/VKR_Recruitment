package com.trueman.recruitment.repositories;

import com.trueman.recruitment.models.User;
import com.trueman.recruitment.models.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    @Query("SELECT v FROM Vacancy v JOIN v.userList u WHERE u.id = :userId")
    List<Vacancy> findVacanciesByUserId(@Param("userId") Long userId);
}
