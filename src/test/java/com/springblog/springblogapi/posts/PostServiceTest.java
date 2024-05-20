package com.springblog.springblogapi.posts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.springblog.springblogapi.comments.Comment;
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
    void canDeletePost() {
        // given
        UUID id = UUID.randomUUID();

        // when
        underTest.deletePost(id);

        // then
        ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(postRepository).deleteById(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(id);
    }

    @Test
    void canGetDraftPosts() {
        underTest.getDraftPosts();

        verify(postRepository).findManyByStatus(Status.DRAFT);
    }

    @Test
    void canGetPost() {
        // given
        UUID id = UUID.randomUUID();
        Post post = new Post();
        given(postRepository.findById(id)).willReturn(Optional.of(post));

        // when
        Post result = underTest.getPost(id);

        // then
        ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(postRepository).findById(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(id);
        assertThat(result).isEqualTo(post);
    }

    @Test
    void getPost_willThrowExceptionIfPostNotFound() {
        // given
        UUID id = UUID.randomUUID();
        given(postRepository.findById(id)).willReturn(Optional.empty());

        // when -> then
        assertThatThrownBy(() -> underTest.getPost(id))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void canGetPostComments() {
        // given
        UUID id = UUID.randomUUID();
        Post post = mock(Post.class);
        Set<Comment> comments = new HashSet<>(Arrays.asList(new Comment("alan1", "body1")));
        given(postRepository.findById(id)).willReturn(Optional.of(post));
        given(post.getComments()).willReturn(comments);

        // when
        Set<Comment> testComments = underTest.getPostComments(id);

        // then
        assertThat(testComments).isEqualTo(comments);
    }

    @Test
    void getPostComments_willThrowExceptionIfPostNotFound() {
        // given
        UUID id = UUID.randomUUID();

        given(postRepository.findById(id)).willReturn(Optional.empty());

        // when -> then
        assertThatThrownBy(() -> underTest.getPostComments(id))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void canGetAllPosts() {
        underTest.getPosts();

        verify(postRepository).findAll();
    }

    @Test
    void canGetPublicPosts() {
        underTest.getPublicPosts();

        verify(postRepository).findManyByStatus(Status.PUBLIC);
    }

    @Test
    void canUpdatePost() {
        // given
        UUID id = UUID.randomUUID();
        Post post = mock(Post.class);
        given(postRepository.findById(id)).willReturn(Optional.of(post));

        // when
        underTest.updatePost(id, "t", "b", Status.PUBLIC);

        // then
        ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(postRepository).findById(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(id);

        verify(post).setTitle("t");
        verify(post).setBody("b");
        verify(post).setStatus(Status.PUBLIC);

        verify(postRepository).save(post);
    }

    @Test
    void updatePost_wontSetFieldsIfNull() {
        // given
        UUID id = UUID.randomUUID();
        Post post = mock(Post.class);
        given(postRepository.findById(id)).willReturn(Optional.of(post));

        // when
        underTest.updatePost(id, null, null, null);

        // then
        ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(postRepository).findById(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(id);

        verify(post, never()).setTitle(anyString());
        verify(post, never()).setBody(anyString());
        verify(post, never()).setStatus(any(Status.class));

        verify(postRepository).save(post);
    }

    @Test
    void updatePost_willThrowExceptionIfPostNotFound() {
        // given
        UUID id = UUID.randomUUID();

        given(postRepository.findById(id)).willReturn(Optional.empty());

        // when -> then
        assertThatThrownBy(() -> underTest.updatePost(id, "t", "b", Status.DRAFT))
                .isInstanceOf(NoSuchElementException.class);
    }
}
