package com.trilogyed.comment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.trilogyed.comment.dao.CommentDao;
import com.trilogyed.comment.model.Comment;
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

@RunWith(SpringRunner.class)
@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @MockBean
    CommentDao dao;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getCommentsByPost() throws Exception{
        Comment comment = new Comment(101,1, LocalDate.of(2019, 10, 10), "Jim", "Hi There");
        List<Comment> comments = Collections.singletonList(comment);
        when(dao.getCommentsByPost(1)).thenReturn(comments);

        String outputJson = mapper.writeValueAsString(comments);

        mockMvc.perform(get("/posts/1/comments"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void getAllComments() throws Exception{

        Comment comment = new Comment(101,1, LocalDate.of(2019, 10, 10), "Jim", "Hi There");
        List<Comment> comments = Collections.singletonList(comment);
        when(dao.getAllComments()).thenReturn(comments);

        String outputJson = mapper.writeValueAsString(comments);

        mockMvc.perform(get("/comments"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void getComment() throws Exception{

        Comment comment = new Comment(101,1, LocalDate.of(2019, 10, 10), "Jim", "Hi There");
        when(dao.getComment(comment.getCommentId())).thenReturn(comment);

        String outputJson =  mapper.writeValueAsString(comment);

        mockMvc.perform(get("/comments/101"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

        mockMvc.perform(get("/comments/9999"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void createComment() throws Exception{
        Comment commentNoId = new Comment(1, LocalDate.of(2019, 10, 10), "Jim", "Hi There");
        Comment comment = new Comment(101,1, LocalDate.of(2019, 10, 10), "Jim", "Hi There");
        when(dao.createComment(commentNoId)).thenReturn(comment);

        String inputJson = mapper.writeValueAsString(commentNoId);
        String outputJson = mapper.writeValueAsString(comment);

        mockMvc.perform(post("/comments")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void updateComment() throws Exception{
        Comment comment = new Comment(101,1, LocalDate.of(2019, 10, 10), "Jim", "Hi There");

        String inputJson = mapper.writeValueAsString(comment);

        this.mockMvc.perform(put("/comments")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    public void deleteComment() throws Exception{

        this.mockMvc.perform(delete("/comments/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void createCommentShouldThrowExceptionWithBadInput() throws Exception{
        Comment comment = new Comment(101,1, null,"Jim", "Hi There");

        String inputJson = mapper.writeValueAsString(comment);

        this.mockMvc.perform(post("/comments")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        inputJson = "badjsonExample";

        this.mockMvc.perform(post("/comments")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());



    }
}