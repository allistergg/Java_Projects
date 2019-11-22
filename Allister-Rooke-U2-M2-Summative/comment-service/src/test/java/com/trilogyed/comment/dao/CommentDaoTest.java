package com.trilogyed.comment.dao;

import com.trilogyed.comment.model.Comment;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentDaoTest {

    @Autowired
    CommentDao dao;

    @Before
    public void setUp() throws Exception {
        dao.getAllComments().forEach(comment -> dao.deleteComment(comment.getCommentId()));
    }

    @Test
    public void createGetComment() {
        Comment comment = new Comment(1, LocalDate.of(2019, 10, 10), "Jim", "Hi There");
        comment = dao.createComment(comment);
        Comment fromDao = dao.getComment(comment.getCommentId());
        assertEquals(comment, fromDao);
        fromDao = dao.getComment(9999);
        assertNull(fromDao);

    }

    @Test
    public void getAllComments() {
        Comment comment = new Comment(1, LocalDate.of(2019, 10, 10), "Jim", "Hi There");
        List<Comment> comments = Collections.singletonList(comment);
        dao.createComment(comment);
        List<Comment> fromDao = dao.getAllComments();
        assertEquals(fromDao, comments);
    }

    @Test
    public void getCommentsByPost() {
        Comment comment = new Comment(1, LocalDate.of(2019, 10, 10), "Jim", "Hi There");
        List<Comment> comments = Collections.singletonList(comment);
        dao.createComment(comment);
        List<Comment> fromDao = dao.getCommentsByPost(comment.getPostId());
        assertEquals(fromDao, comments);

    }



    @Test
    public void updateComment() {
        Comment comment = new Comment(1, LocalDate.of(2019, 10, 10), "Jim", "Hi There");
        dao.createComment(comment);
        comment.setComment("Hi There - edited");
        dao.updateComment(comment);
        Comment fromDao = dao.getComment(comment.getCommentId());
        assertEquals(comment, fromDao);
    }

    @Test
    public void deleteComment() {
        Comment comment = new Comment(1, LocalDate.of(2019, 10, 10), "Jim", "Hi There");
        comment = dao.createComment(comment);
        Comment fromDao = dao.getComment(comment.getCommentId());
        assertEquals(comment, fromDao);
        dao.deleteComment(comment.getCommentId());
        fromDao = dao.getComment(comment.getCommentId());
        assertNull(fromDao);

    }
}