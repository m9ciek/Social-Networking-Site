package com.maciek.socialnetworkingsite.dao;

import com.maciek.socialnetworkingsite.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository  extends JpaRepository<Post, Long> {
}
