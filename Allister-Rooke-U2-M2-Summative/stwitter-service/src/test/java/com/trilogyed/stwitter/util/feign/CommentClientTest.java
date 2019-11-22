package com.trilogyed.stwitter.util.feign;

import com.trilogyed.stwitter.model.Comment;
import feign.FeignException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentClientTest {

    @Autowired
    CommentClient commentClient;

    @Autowired
    PostClient postClient;

    @Before
    public void setUp() throws Exception {

        commentClient.getAllComments().forEach(comment -> commentClient.deleteComment(comment.getCommentId()));
    }



    @Test
    public void getAllComments() {
        Comment comment = new Comment(1, LocalDate.of(2019,10,10), "Jim", "Hi there");
        comment = commentClient.createComment(comment);
        List<Comment> comments = Collections.singletonList(comment);
        List<Comment> fromFeign = commentClient.getAllComments();
        assertEquals(comments, fromFeign);


    }

    @Test
    public void CreateGetDeleteComment() {
        Comment comment = new Comment(1, LocalDate.of(2019,10,10), "Jim", "Hi there");
        comment = commentClient.createComment(comment);
        Comment fromFeign = commentClient.getComment(comment.getCommentId());
        assertEquals(comment, fromFeign);
        commentClient.deleteComment(comment.getCommentId());
        boolean feignError = false;
        try {
            commentClient.getComment(comment.getCommentId());
        } catch(FeignException e) {
            feignError = true;
        }

        assertTrue(feignError);
    }

    @Test
    public void getCommentsByPost() {
        Comment comment = new Comment(1, LocalDate.of(2019, 10, 10), "Jim", "Hi There");
        comment = commentClient.createComment(comment);
        List<Comment> comments = Collections.singletonList(comment);
        List<Comment> fromFeign = commentClient.getCommentsByPost(comment.getPostId());
        assertEquals(fromFeign, comments);

    }
}