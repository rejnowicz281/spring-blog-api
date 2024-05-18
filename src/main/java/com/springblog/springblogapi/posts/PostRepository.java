package com.springblog.springblogapi.posts;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springblog.springblogapi.posts.enums.Status;

public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findManyByStatus(Status status);
}
