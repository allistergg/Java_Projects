package com.trilogyed.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.trilogyed.post.dao.PostDao;
import com.trilogyed.post.model.Post;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@RunWith(SpringRunner.class)
public class PostControllerTest {

    @MockBean
    PostDao dao;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void createPost() throws Exception{
        Post postNoId = new Post( LocalDate.of(2019,10,10), "John", "Hello World");
        Post post = new Post(1, LocalDate.of(2019,10,10), "John", "Hello World");
        when(dao.createPost(postNoId)).thenReturn(post);

        String inputJson = mapper.writeValueAsString(postNoId);
        String outputJson = mapper.writeValueAsString(post);

        mockMvc.perform(post("/posts")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void getPost() throws Exception {
        Post post = new Post(1, LocalDate.of(2019,10,10), "John", "Hello World");
        when(dao.getPost(post.getPostId())).thenReturn(post);

        String outputJson = mapper.writeValueAsString(post);

        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

        mockMvc.perform(get("/posts/9999"))
                .andExpect(status().isNotFound());


    }

    @Test
    public void getAllPosts() throws Exception{
        Post post = new Post(1, LocalDate.of(2019,10,10), "John", "Hello World");
        List<Post> posts = Collections.singletonList(post);
        when(dao.getAllPosts()).thenReturn(posts);

        String outputJson = mapper.writeValueAsString(posts);

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void updatePost() throws Exception{
        Post post = new Post(1, LocalDate.of(2019,10,10), "John", "Hello World");

        String inputJson = mapper.writeValueAsString(post);

        mockMvc.perform(put("/posts")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    public void deletePost() throws Exception{

        mockMvc.perform(delete("/posts/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void createPostShouldThrowErrorWithBadInput() throws Exception{
        Post post = new Post(null, "John", "Hello World");

        String inputJson = mapper.writeValueAsString(post);


        this.mockMvc.perform(post("/posts")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        inputJson = "badjsonExample";

        this.mockMvc.perform(post("/posts")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}