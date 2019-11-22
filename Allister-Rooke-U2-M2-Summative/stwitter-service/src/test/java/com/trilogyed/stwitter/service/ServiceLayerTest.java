package com.trilogyed.stwitter.service;

import com.trilogyed.stwitter.model.Comment;
import com.trilogyed.stwitter.model.Post;
import com.trilogyed.stwitter.util.feign.CommentClient;
import com.trilogyed.stwitter.util.feign.PostClient;
import com.trilogyed.stwitter.viewmodel.PostViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class ServiceLayerTest {

    @InjectMocks
    ServiceLayer service;
    @Mock
    PostClient client;
    @Mock
    CommentClient commentClient;
    @Mock
    RabbitTemplate rabbit;

    public static final String EXCHANGE = "comment-exchange";
    public static final String ROUTING_KEY = "comment.create";





    @Before
    public void setUp() throws Exception {
        Post post = new Post(1, LocalDate.of(2019,10,10), "John", "Hello World");
        Post postNoId = new Post(LocalDate.of(2019,10,10), "John", "Hello World");
        List<Post> posts = Collections.singletonList(post);
        doReturn(post).when(client).createPost(postNoId);
        doReturn(Optional.of(post)).when(client).getPost(post.getPostId());
        doReturn(Optional.empty()).when(client).getPost(9999);
        doReturn(posts).when(client).getAllPosts();

    }

    @Test
    public void createPostShouldReturnCreatedPostViewModel() {
        PostViewModel pvm = new PostViewModel(LocalDate.of(2019,10,10), "John", "Hello World");
        PostViewModel fromService = service.createPost(pvm);
        pvm.setPostId(fromService.getPostId());
        pvm.setComments(fromService.getComments());
        assertEquals(pvm, fromService);
    }

    @Test
    public void findPostByIdShouldReturnPostViewModelIfPostWithGivenIdIfExists() {
        PostViewModel pvm = new PostViewModel(1, LocalDate.of(2019,10,10), "John", "Hello World", new ArrayList<>());
        Optional<PostViewModel> fromService = service.findPostById(pvm.getPostId());
        assertEquals(pvm, fromService.get());
    }

    @Test
    public void findPostByIdShouldReturnEmptyOptionalIfPostWithGivenIdDoesNotExist() {
        Optional<PostViewModel> fromService = service.findPostById(9999);
        assertFalse(fromService.isPresent());
    }

    @Test
    public void findAllPostsShouldReturnAllPosts() {
        PostViewModel pvm = new PostViewModel(1, LocalDate.of(2019,10,10), "John", "Hello World", new ArrayList<>());
        List<PostViewModel> posts = Collections.singletonList(pvm);
        List<PostViewModel> fromService = service.findAllPosts();
        assertEquals(posts, fromService);
    }

    @Test
    public void createCommentShouldSendCommentToRabbitMQ() {
        Comment comment = new Comment(1, LocalDate.of(2019,10,10), "Jim", "Hi there");
        service.createComment(comment);
        verify(rabbit, times(1)).convertAndSend(EXCHANGE, ROUTING_KEY, comment);
    }
}