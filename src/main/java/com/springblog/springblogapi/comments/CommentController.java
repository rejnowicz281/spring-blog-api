package com.springblog.springblogapi.comments;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getComments() {
        return commentService.getComments();
    }

    @GetMapping(path = "{id}")
    public Comment getComment(@PathVariable("id") UUID id) {
        return commentService.getComment(id);
    }

    @PostMapping(path = "{post_id}")
    public void createComment(@PathVariable("post_id") UUID post_id, @RequestBody Comment comment) {
        commentService.createComment(post_id, comment.getAuthor(), comment.getBody());
    }

    @PutMapping(path = "{id}")
    public void updateComment(@PathVariable("id") UUID id, @RequestBody Comment comment) {
        commentService.updateComment(id, comment.getAuthor(), comment.getBody());
    }

    @DeleteMapping(path = "{id}")
    public void deleteComment(@PathVariable("id") UUID id) {
        commentService.deleteComment(id);
    }

}
