package com.trueman.recruitment.repositories;

import com.trueman.recruitment.models.ResponseToResume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ResponseToResumeRepository extends JpaRepository<ResponseToResume, Long> {
    ResponseToResume findByUserIdAndResumeId(@Param("userId") Long userId, @Param("resumeId") Long resumeId);

    Page<ResponseToResume> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT COUNT(r) FROM ResponseToResume r WHERE r.responseDate >= :startDate AND r.responseDate <= :endDate AND r.user.id =:userId")
    int countAllFoundResume(@Param("startDate") LocalDate startDate,
                            @Param("endDate") LocalDate endDate,
                            @Param("userId") Long userId);
}
