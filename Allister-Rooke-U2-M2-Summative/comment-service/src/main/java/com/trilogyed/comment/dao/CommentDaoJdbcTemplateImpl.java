package com.trilogyed.comment.dao;

import com.trilogyed.comment.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDaoJdbcTemplateImpl implements CommentDao {

    public static final String INSERT_COMMENT_SQL =
            "INSERT INTO comment (post_id, create_date, commenter_name, comment) VALUES (?,?,?,?)";
    public static final String SELECT_COMMENT_BY_ID_SQL =
            "SELECT * FROM comment WHERE comment_id = ?";
    public static final String SELECT_ALL_COMMENTS_SQL =
            "SELECT * FROM comment";
    public static final String SELECT_COMMENTS_BY_POST_SQL =
            "SELECT * FROM comment WHERE post_id = ?";
    public static final String UPDATE_COMMENT_SQL =
            "UPDATE comment SET post_id = ?, create_date = ?, commenter_name = ?, comment = ? WHERE comment_id = ?";
    public static final String DELETE_COMMENT_SQL =
            "DELETE FROM comment WHERE comment_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    public CommentDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Comment createComment(Comment comment) {
        jdbcTemplate.update(INSERT_COMMENT_SQL,
                comment.getPostId(),
                comment.getCreateDate(),
                comment.getCommenterName(),
                comment.getComment());
        comment.setCommentId(jdbcTemplate.queryForObject("SELECT last_insert_id()", Integer.class));
        return comment;
    }

    @Override
    public Comment getComment(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_COMMENT_BY_ID_SQL, new BeanPropertyRowMapper<>(Comment.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Comment> getAllComments() {
        return jdbcTemplate.query(SELECT_ALL_COMMENTS_SQL, new BeanPropertyRowMapper<>(Comment.class));
    }

    @Override
    public List<Comment> getCommentsByPost(int postId) {
        return jdbcTemplate.query(SELECT_COMMENTS_BY_POST_SQL, new BeanPropertyRowMapper<>(Comment.class), postId);
    }

    @Override
    public void updateComment(Comment comment) {
        jdbcTemplate.update(UPDATE_COMMENT_SQL,
                comment.getPostId(),
                comment.getCreateDate(),
                comment.getCommenterName(),
                comment.getComment(),
                comment.getCommentId());
    }

    @Override
    public void deleteComment(int id) {
        jdbcTemplate.update(DELETE_COMMENT_SQL, id);
    }
}
