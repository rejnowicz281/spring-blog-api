package com.springblog.springblogapi.posts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.springblog.springblogapi.posts.enums.Status;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    private PostRepository postRepository;
    private PostService underTest;

    @BeforeEach
    void setUp() {
        underTest = new PostService(postRepository);
    }

    @Test
    void canCreatePost() {
        // given
        String title = "post1";
        String body = "body1";
        Status status = Status.DRAFT;

        // when
        underTest.createPost(title, body, status);

        // then
        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);

        verify(postRepository).save(postArgumentCaptor.capture());

        Post capturedPost = postArgumentCaptor.getValue();

        assertThat(capturedPost.getTitle()).isEqualTo(title);
        assertThat(capturedPost.getBody()).isEqualTo(body);
        assertThat(capturedPost.getStatus()).isEqualTo(Status.DRAFT);
    }

    @Test
    void testDeletePost() {

    }

    @Test
    void testGetDraftPosts() {

    }

    @Test
    void testGetPost() {

    }

    @Test
    void testGetPostComments() {

    }

    @Test
    void canGetAllPosts() {
        underTest.getPosts();

        verify(postRepository).findAll();
    }

    @Test
    void testGetPublicPosts() {

    }

    @Test
    void testUpdatePost() {

    }
}
