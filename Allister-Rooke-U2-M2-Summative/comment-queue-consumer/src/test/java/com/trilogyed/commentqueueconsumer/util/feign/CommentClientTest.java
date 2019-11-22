package com.trilogyed.commentqueueconsumer.util.feign;

import com.trilogyed.commentqueueconsumer.model.Comment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentClientTest {

    @Autowired
    CommentClient client;

    @Before
    public void setUp() throws Exception {
    }



    @Test
    public void createComment() {
        Comment comment = new Comment(1, LocalDate.of(2019, 10, 10), "Jim", "Hi There");
        Comment fromFeign = client.createComment(comment);
        assertEquals(comment, fromFeign);
    }
}