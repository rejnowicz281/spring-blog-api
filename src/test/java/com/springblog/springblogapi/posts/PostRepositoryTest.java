package com.springblog.springblogapi.posts;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.springblog.springblogapi.posts.enums.Status;

@DataJpaTest
public class PostRepositoryTest {
    @Autowired
    private PostRepository underTest;

    @AfterEach
    void deleteAll() {
        underTest.deleteAll();
    }

    @Test
    void itShouldFindManyByStatus() {
        // given
        Post draft1 = new Post("draft1", "body1", Status.DRAFT);
        Post draft2 = new Post("draft2", "body1", Status.DRAFT);
        Post public1 = new Post("public1", "body22", Status.PUBLIC);
        Post public2 = new Post("public2", "body22", Status.PUBLIC);

        // when
        underTest.save(draft1);
        underTest.save(draft2);
        underTest.save(public1);
        underTest.save(public2);

        // then
        List<Post> draftPosts = underTest.findManyByStatus(Status.DRAFT);
        List<Post> publicPosts = underTest.findManyByStatus(Status.PUBLIC);

        assertThat(draftPosts).allMatch(post -> post.getStatus() == Status.DRAFT);
        assertThat(publicPosts).allMatch(post -> post.getStatus() == Status.PUBLIC);
    }
}
