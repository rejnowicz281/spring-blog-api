package com.springblog.springblogapi.posts;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springblog.springblogapi.posts.enums.Status;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findManyByStatus(Status status);
}
