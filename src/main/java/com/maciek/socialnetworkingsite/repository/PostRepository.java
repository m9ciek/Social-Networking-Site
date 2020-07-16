package com.maciek.socialnetworkingsite.repository;

import com.maciek.socialnetworkingsite.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository  extends JpaRepository<Post, Long> {

    List<Post> findAllByUserIdIn(List<Long> userIds);

    @Query("SELECT p FROM Post p")
    List<Post> findAllPosts(Pageable page);
}
