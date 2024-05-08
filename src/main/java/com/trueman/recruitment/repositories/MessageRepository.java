package com.trueman.recruitment.repositories;

import com.trueman.recruitment.models.Message;
import com.trueman.recruitment.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE (m.sender = :senderId AND m.receiver = :receiverId) OR (m.sender = :receiverId AND m.receiver = :senderId) ORDER BY m.currentDateTime DESC")
    List<Message> findBySenderIdAndReceiverIdOrderByCurrentDateTimeDesc(Long senderId, Long receiverId);

    @Query("SELECT DISTINCT m.receiver FROM Message m WHERE m.sender = :id")
    List<Long> findUserChats(@Param("id") Long id);
}
