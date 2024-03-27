package com.trueman.recruitment.repositories;

import com.trueman.recruitment.models.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
}
