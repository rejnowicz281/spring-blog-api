package com.springblog.springblogapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.springblog.springblogapi.comments.Comment;
import com.springblog.springblogapi.comments.CommentRepository;
import com.springblog.springblogapi.posts.Post;
import com.springblog.springblogapi.posts.PostRepository;
import com.springblog.springblogapi.posts.enums.Status;

@Component
public class DataLoader implements CommandLineRunner {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public DataLoader(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Post post1 = new Post("Title 1", "Content 1", Status.DRAFT);
        Post post2 = new Post("Title 2", "Content 2", Status.PUBLIC);

        postRepository.save(post1);
        postRepository.save(post2);

        Comment comment1 = new Comment("Author 1", "Body 1");
        Comment comment2 = new Comment("Author 2", "Body 2");

        comment1.setPost(post1);
        comment2.setPost(post2);

        commentRepository.save(comment1);
        commentRepository.save(comment2);
    }
}