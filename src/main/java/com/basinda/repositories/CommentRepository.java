package com.basinda.repositories;

import com.basinda.models.entity.Comment;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUserId(String userId);
    List<Comment> findByOwnerId(String ownerId);
}