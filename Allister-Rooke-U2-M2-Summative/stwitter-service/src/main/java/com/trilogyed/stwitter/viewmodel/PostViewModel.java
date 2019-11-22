package com.trilogyed.stwitter.viewmodel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.trilogyed.stwitter.model.Comment;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class PostViewModel implements Serializable {

    private Integer postId;
    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate postDate;
    @NotEmpty
    private String posterName;
    private String post;
    private List<Comment> comments;

    public PostViewModel() {
    }

    public PostViewModel(LocalDate postDate, String posterName, String post) {
        this.postDate = postDate;
        this.posterName = posterName;
        this.post = post;
    }

    public PostViewModel(Integer postId, LocalDate postDate, String posterName, String post, List<Comment> comments) {
        this.postId = postId;
        this.postDate = postDate;
        this.posterName = posterName;
        this.post = post;
        this.comments = comments;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostViewModel that = (PostViewModel) o;
        return getPostDate().equals(that.getPostDate()) &&
                getPosterName().equals(that.getPosterName()) &&
                Objects.equals(getPost(), that.getPost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPostDate(), getPosterName(), getPost());
    }

    @Override
    public String toString() {
        return "PostViewModel{" +
                "postId=" + postId +
                ", postDate=" + postDate +
                ", posterName='" + posterName + '\'' +
                ", post='" + post + '\'' +
                ", comments=" + comments +
                '}';
    }
}
