package com.trueman.recruitment.repositories;

import com.trueman.recruitment.models.ResponseToResume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResponseToResumeRepository extends JpaRepository<ResponseToResume, Long> {
    ResponseToResume findByUserIdAndResumeId(@Param("userId") Long userId, @Param("resumeId") Long resumeId);

    Page<ResponseToResume> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
