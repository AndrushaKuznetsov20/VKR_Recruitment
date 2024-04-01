package com.trueman.recruitment.repositories;

import com.trueman.recruitment.models.Message;
import com.trueman.recruitment.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE (m.sender = :senderId AND m.receiver = :receiverId) OR (m.sender = :receiverId AND m.receiver = :senderId)")
    List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
