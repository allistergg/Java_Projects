package com.trilogyed.post.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class Post {

    private Integer postId;
    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate postDate;
    @NotEmpty
    @Length(max = 50)
    private String posterName;
    @Length(max = 255)
    private String post;

    public Post() {
    }

    public Post(Integer postId, @NotNull LocalDate postDate, @NotEmpty @Length(max = 50) String posterName, @Length(max = 255) String post) {
        this.postId = postId;
        this.postDate = postDate;
        this.posterName = posterName;
        this.post = post;
    }

    public Post(@NotNull LocalDate postDate, @NotEmpty @Length(max = 50) String posterName, @Length(max = 255) String post) {
        this.postDate = postDate;
        this.posterName = posterName;
        this.post = post;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public String getPosterName() {
        return posterName;
    }

    public void setPosterName(String posterName) {
        this.posterName = posterName;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post1 = (Post) o;
        return getPostDate().equals(post1.getPostDate()) &&
                getPosterName().equals(post1.getPosterName()) &&
                Objects.equals(getPost(), post1.getPost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPostDate(), getPosterName(), getPost());
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", postDate=" + postDate +
                ", posterName='" + posterName + '\'' +
                ", post='" + post + '\'' +
                '}';
    }
}




