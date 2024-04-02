package com.trueman.recruitment.repositories;

import com.trueman.recruitment.models.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findByVacancyId(@Param("vacancyId") Long vacancyId);
    Response findByUserIdAndVacancyId(@Param("userId") Long userId, @Param("vacancyId") Long vacancyId);

}
