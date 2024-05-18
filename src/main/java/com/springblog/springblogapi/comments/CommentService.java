package com.springblog.springblogapi.comments;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    public Comment getComment(UUID id) {
        Comment comment = commentRepository.findById(id).orElseThrow();

        return comment;
    }

    public void createComment(String author, String body) {
        Comment comment = new Comment(author, body);

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
