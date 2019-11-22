package com.trilogyed.comment.dao;

import com.trilogyed.comment.model.Comment;

import java.util.List;

public interface CommentDao {

    public Comment createComment(Comment comment);
    public Comment getComment(int id);
    public List<Comment> getAllComments();
    public List<Comment> getCommentsByPost(int postId);
    public void updateComment(Comment comment);
    public void deleteComment(int id);
}
