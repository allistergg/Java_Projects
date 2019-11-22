package com.trilogyed.stwitter.util.feign;

import com.trilogyed.stwitter.model.Post;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


@SpringBootTest
@RunWith(SpringRunner.class)
public class PostClientTest {

    @Autowired
    PostClient postClient;

    @Before
    public void setUp() throws Exception {
        postClient.getAllPosts().forEach(post -> postClient.deletePost(post.getPostId()));
    }

    @Test
    public void createGetDeletePost() {
        Post post = new Post(LocalDate.of(2019,10,10), "John", "Hello World");
        post = postClient.createPost(post);
        Optional<Post> fromFeign = postClient.getPost(post.getPostId());
        assertEquals(post, fromFeign.get());
        postClient.deletePost(post.getPostId());
        fromFeign = postClient.getPost(post.getPostId());
        assertFalse(fromFeign.isPresent());

    }

    @Test
    public void getAllPosts() {
        Post post = new Post(LocalDate.of(2019,10,10), "John", "Hello World");
        post = postClient.createPost(post);
        List<Post> posts = Collections.singletonList(post);
        List<Post> fromFeign = postClient.getAllPosts();
        assertEquals(posts, fromFeign);
    }
}