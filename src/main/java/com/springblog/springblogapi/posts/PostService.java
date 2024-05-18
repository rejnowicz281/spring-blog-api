package com.springblog.springblogapi.posts;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.springblog.springblogapi.posts.enums.Status;

import jakarta.transaction.Transactional;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    public List<Post> getPublicPosts() {
        return postRepository.findManyByStatus(Status.PUBLIC);
    }

    public List<Post> getDraftPosts() {
        return postRepository.findManyByStatus(Status.DRAFT);
    }

    public Post getPost(UUID id) {
        Post post = postRepository.findById(id).orElseThrow();

        return post;
    }

    public void createPost(String title, String body, Status status) {
        Post post = new Post(title, body, status);

        postRepository.save(post);
    }

    @Transactional
    public void updatePost(UUID id, String title, String body, Status status) {
        Post post = postRepository.findById(id).orElseThrow();

        if (title != null) {
            post.setTitle(title);
        }

        if (body != null) {
            post.setBody(body);
        }

        if (status != null) {
            post.setStatus(status);
        }

        postRepository.save(post);
    }

    public void deletePost(UUID id) {
        postRepository.deleteById(id);
    }
}
