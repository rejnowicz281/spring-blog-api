package com.springblog.springblogapi.comments;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.springblog.springblogapi.posts.Post;
import com.springblog.springblogapi.posts.PostRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postrepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postrepository;
    }

    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    public Comment getComment(UUID id) {
        Comment comment = commentRepository.findById(id).orElseThrow();

        return comment;
    }

    public void createComment(UUID post_id, String author, String body) {
        Comment comment = new Comment(author, body);
        Post post = postRepository.findById(post_id).orElseThrow();
        comment.setPost(post);
        commentRepository.save(comment);
    }

    public void updateComment(UUID id, String author, String body) {
        Comment comment = commentRepository.findById(id).orElseThrow();

        if (author != null) {
            comment.setAuthor(author);
        }

        if (body != null) {
            comment.setBody(body);
        }

        commentRepository.save(comment);
    }

    public void deleteComment(UUID id) {
        commentRepository.deleteById(id);
    }
}
