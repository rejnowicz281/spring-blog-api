package com.springblog.springblogapi.comments;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    private UUID id;

    @Column(length = 100)
    private String author;

    @Column(length = 10000, nullable = false)
    private String body;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Override
    public String toString() {
        return "Comment [id=" + id + ", author=" + author + ", body=" + body + ", createdAt="
                + createdAt + ", updatedAt=" + updatedAt + "]";
    }

    public Comment(String author, String body) {
        this.id = UUID.randomUUID();
        this.author = author;
        this.body = body;
    }

    public Comment() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
