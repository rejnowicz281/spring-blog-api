package com.springblog.springblogapi.posts;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springblog.springblogapi.comments.Comment;

@RequestMapping("api/v1/posts")
@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("public")
    public List<Post> getPublicPosts() {
        return postService.getPublicPosts();
    }

    @GetMapping("draft")
    public List<Post> getDraftPosts() {
        return postService.getDraftPosts();
    }

    @GetMapping(path = "{id}")
    public Post getPost(@PathVariable("id") UUID id) {
        return postService.getPost(id);
    }

    @GetMapping(path = "{id}/comments")
    public Set<Comment> getPostComments(@PathVariable("id") UUID id) {
        return postService.getPostComments(id);
    }

    @PostMapping()
    public void createPost(@RequestBody Post post) {
        postService.createPost(post.getTitle(), post.getBody(), post.getStatus());
    }

    @PutMapping(path = "{id}")
    public void updatePost(@PathVariable("id") UUID id, @RequestBody Post post) {
        postService.updatePost(id, post.getTitle(), post.getBody(), post.getStatus());
    }

    @DeleteMapping(path = "{id}")
    public void deletePost(@PathVariable("id") UUID id) {
        postService.deletePost(id);
    }

}
