package com.trueman.recruitment.repositories;

import com.trueman.recruitment.models.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface ResumeRepository extends JpaRepository<Resume, Long>, JpaSpecificationExecutor<Resume> {
    List<Resume> findAll();

    @Query("SELECT r FROM Resume r WHERE r.statusResume = 'Опубликовано!'")
    Page<Resume> findAllStatusOk(Pageable pageable);

    Optional<Resume> findByUserId(@Param("userId") Long userId);

}
