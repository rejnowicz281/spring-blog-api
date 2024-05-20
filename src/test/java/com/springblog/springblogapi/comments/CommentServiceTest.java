package com.springblog.springblogapi.comments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.springblog.springblogapi.posts.Post;
import com.springblog.springblogapi.posts.PostRepository;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;
    private CommentService underTest;

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        underTest = new CommentService(commentRepository, postRepository);
    }

    @Test
    void canGetComment() {
        UUID id = UUID.randomUUID();
        Comment comment = new Comment();
        given(commentRepository.findById(id)).willReturn(Optional.of(comment));

        // when
        Comment result = underTest.getComment(id);

        // then
        ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(commentRepository).findById(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(id);
        assertThat(result).isEqualTo(comment);
    }

    @Test
    void getComment_willThrowExceptionIfCommentNotFound() {
        // given
        UUID id = UUID.randomUUID();
        given(commentRepository.findById(id)).willReturn(Optional.empty());

        // when -> then
        assertThatThrownBy(() -> underTest.getComment(id))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void canCreateComment() {
        // given
        UUID post_id = UUID.randomUUID();
        String author = "author1";
        String body = "body1";
        Post post = mock(Post.class);
        given(postRepository.findById(post_id)).willReturn(Optional.of(post));
        given(post.getId()).willReturn(post_id);

        // when
        underTest.createComment(post_id, author, body);

        // then
        ArgumentCaptor<Comment> argumentCaptor = ArgumentCaptor.forClass(Comment.class);

        verify(commentRepository).save(argumentCaptor.capture());

        Comment capturedComment = argumentCaptor.getValue();

        assertThat(capturedComment.getAuthor()).isEqualTo(author);
        assertThat(capturedComment.getBody()).isEqualTo(body);
        assertThat(capturedComment.getPostId()).isEqualTo(post_id);
    }

    @Test
    void createComment_willThrowExceptionIfPostNotFound() {
        // given
        UUID id = UUID.randomUUID();
        given(postRepository.findById(id)).willReturn(Optional.empty());

        // when -> then
        assertThatThrownBy(() -> underTest.createComment(id, "a", "b"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void canDeleteComment() {
        // given
        UUID id = UUID.randomUUID();

        // when
        underTest.deleteComment(id);

        // then
        ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(commentRepository).deleteById(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(id);
    }

    @Test
    void canGetAllComments() {
        underTest.getComments();

        verify(commentRepository).findAll();
    }

    @Test
    void canUpdateComment() {
        // given
        UUID id = UUID.randomUUID();
        Comment comment = mock(Comment.class);
        given(commentRepository.findById(id)).willReturn(Optional.of(comment));

        // when
        underTest.updateComment(id, "a", "b");

        // then
        ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(commentRepository).findById(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(id);

        verify(comment).setAuthor("a");
        verify(comment).setBody("b");

        verify(commentRepository).save(comment);
    }

    @Test
    void updateComment_wontSetFieldsIfNull() {
        // given
        UUID id = UUID.randomUUID();
        Comment comment = mock(Comment.class);
        given(commentRepository.findById(id)).willReturn(Optional.of(comment));

        // when
        underTest.updateComment(id, null, null);

        // then
        ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(commentRepository).findById(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(id);

        verify(comment, never()).setAuthor(anyString());
        verify(comment, never()).setBody(anyString());

        verify(commentRepository).save(comment);
    }

    @Test
    void updateComment_willThrowExceptionIfCommentNotFound() {
        // given
        UUID id = UUID.randomUUID();
        given(commentRepository.findById(id)).willReturn(Optional.empty());

        // when -> then
        assertThatThrownBy(() -> underTest.updateComment(id, "a", "b"))
                .isInstanceOf(NoSuchElementException.class);
    }
}
