package com.maciek.socialnetworkingsite.repository;

import com.maciek.socialnetworkingsite.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
