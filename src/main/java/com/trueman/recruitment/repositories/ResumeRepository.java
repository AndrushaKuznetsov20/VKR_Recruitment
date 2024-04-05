package com.trueman.recruitment.repositories;

import com.trueman.recruitment.models.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    @Query("SELECT r FROM Resume r WHERE r.statusResume: = 'Опубликовано!'")
    List<Resume> findAll();
}
