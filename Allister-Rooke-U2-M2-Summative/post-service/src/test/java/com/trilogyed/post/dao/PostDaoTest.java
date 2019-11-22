package com.trilogyed.post.dao;

import com.trilogyed.post.model.Post;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PostDaoTest {

    @Autowired
    PostDao dao;

    @Before
    public void setUp() throws Exception {
        dao.getAllPosts().forEach(post -> dao.deletePost(post.getPostId()));
    }

    @Test
    public void createGetPost() {
        Post post = new Post(LocalDate.of(2019,10,10), "John", "Hello World");
        post = dao.createPost(post);
        Post fromDao = dao.getPost(post.getPostId());
        assertEquals(fromDao, post);
        fromDao = dao.getPost(9999);
        assertNull(fromDao);
    }

    @Test
    public void getAllPosts() {
        Post post = new Post( LocalDate.of(2019,10,10), "John", "Hello World");
        post = dao.createPost(post);
        List<Post> posts = Collections.singletonList(post);
        List<Post> fromDao = dao.getAllPosts();
        assertEquals(fromDao, posts);
    }

    @Test
    public void updatePost() {
        Post post = new Post( LocalDate.of(2019,10,10), "John", "Hello World");
        post = dao.createPost(post);
        post.setPost("Hello World - edited");
        dao.updatePost(post);
        Post fromDao = dao.getPost(post.getPostId());
        assertEquals(post, fromDao);
    }

    @Test
    public void deletePost() {
        Post post = new Post( LocalDate.of(2019,10,10), "John", "Hello World");
        post = dao.createPost(post);
        Post fromDao = dao.getPost(post.getPostId());
        assertEquals(post, fromDao);
        dao.deletePost(post.getPostId());
        fromDao = dao.getPost(post.getPostId());
        assertNull(fromDao);
    }
}