package com.trilogyed.stwitter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.trilogyed.stwitter.model.Comment;
import com.trilogyed.stwitter.service.ServiceLayer;
import com.trilogyed.stwitter.viewmodel.PostViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(StwitterController.class)
public class StwitterControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ServiceLayer service;

    @MockBean
    RabbitTemplate rabbitTemplate;

    ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void createPostShouldReturnCreatedPostWithValidInput() throws Exception{
        PostViewModel fromClient = new PostViewModel(LocalDate.of(2019,10,10), "John", "Hello World");
        PostViewModel pvm = new PostViewModel(1, LocalDate.of(2019,10,10), "John", "Hello World", new ArrayList<>());
        when(service.createPost(fromClient)).thenReturn(pvm);

        String inputJson = mapper.writeValueAsString(fromClient);
        System.out.println(inputJson);
        String outputJson = mapper.writeValueAsString(pvm);
        System.out.println(outputJson);

        this.mockMvc.perform(post("/")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void getPostByIdShouldReturnPostForValidInput() throws Exception{
        PostViewModel pvm = new PostViewModel(1, LocalDate.of(2019,10,10), "John", "Hello World", new ArrayList<>());
        when(service.findPostById(pvm.getPostId())).thenReturn(Optional.of(pvm));

        String outputJson = mapper.writeValueAsString(pvm);
        this.mockMvc.perform(get("/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }
    @Test
    public void getPostByIdShouldReturn404ForInvalidInput() throws Exception{
        this.mockMvc.perform(get("/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllPosts() throws Exception{

        PostViewModel pvm = new PostViewModel(1, LocalDate.of(2019,10,10), "John", "Hello World", new ArrayList<>());
        List<PostViewModel> pvmList = Collections.singletonList(pvm);
        when(service.findAllPosts()).thenReturn(pvmList);

        String outputJson = mapper.writeValueAsString(pvmList);

        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void createComment() throws Exception{
        Comment comment = new Comment(1, LocalDate.of(2019, 10, 10), "Jim", "Hi There");
        String inputJson = mapper.writeValueAsString(comment);
        this.mockMvc.perform(post("/comment")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON));
        verify(service, times(1)).createComment(comment);
    }

    @Test
    public void CreatePostShouldThrowErrorWithBadInput() throws Exception{
        PostViewModel pvm = new PostViewModel(null, "John", "Hello World");

        String inputJson = mapper.writeValueAsString(pvm);

        mockMvc.perform(post("/")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        inputJson = "badjsonExample";

        this.mockMvc.perform(post("/")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


    }

}