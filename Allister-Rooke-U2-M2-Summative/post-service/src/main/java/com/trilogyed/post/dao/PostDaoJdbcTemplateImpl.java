package com.trilogyed.post.dao;

import com.trilogyed.post.model.Post;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostDaoJdbcTemplateImpl implements PostDao {

    public static final String INSERT_POST_SQL =
            "INSERT INTO post (post_date, poster_name, post) VALUES (?,?,?)";
    public static final String SELECT_POST_BY_ID_SQL =
            "SELECT * FROM post WHERE post_id = ?";
    public static final String SELECT_ALL_POSTS_SQL =
            "SELECT * FROM post";
    public static final String UPDATE_POST_SQL =
            "UPDATE post SET post_date = ?, poster_name = ?, post = ? WHERE post_id = ?";
    public static final String DELETE_POST_SQL =
            "DELETE FROM post WHERE post_id = ?";

    JdbcTemplate jdbcTemplate;

    public PostDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Post createPost(Post post) {
        jdbcTemplate.update(INSERT_POST_SQL,
                post.getPostDate(),
                post.getPosterName(),
                post.getPost());
        post.setPostId(jdbcTemplate.queryForObject("SELECT last_insert_id()", Integer.class));
        return post;
    }

    @Override
    public Post getPost(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_POST_BY_ID_SQL, new BeanPropertyRowMapper<>(Post.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Post> getAllPosts() {
        return jdbcTemplate.query(SELECT_ALL_POSTS_SQL, new BeanPropertyRowMapper<>(Post.class));
    }

    @Override
    public void updatePost(Post post) {
        jdbcTemplate.update(UPDATE_POST_SQL,
                post.getPostDate(),
                post.getPosterName(),
                post.getPost(),
                post.getPostId());
    }

    @Override
    public void deletePost(int id) {
        jdbcTemplate.update(DELETE_POST_SQL, id);
    }
}
