package com.trilogyed.comment.controller;

import com.trilogyed.comment.dao.CommentDao;
import com.trilogyed.comment.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CacheConfig(cacheNames={"comments"})
public class CommentController {

    @Autowired
    CommentDao dao;

    @RequestMapping(value="/posts/{postId}/comments", method= RequestMethod.GET)
    public List<Comment> getCommentsByPost(@PathVariable int postId) {
        return dao.getCommentsByPost(postId);
    }

    @RequestMapping(value="/comments", method=RequestMethod.GET)
    public List<Comment> getAllComments() {
        return dao.getAllComments();
    }

    @Cacheable
    @RequestMapping(value="/comments/{id}", method=RequestMethod.GET)
    public Comment getComment(@PathVariable int id) {
        return Optional.ofNullable(dao.getComment(id)).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Comment not found with id " + id));
    }


    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @CachePut(key="#result.getCommentId()")
    public Comment createComment(@RequestBody @Valid Comment comment) {
        System.out.println("creating comment..");
        return dao.createComment(comment);
    }

    @RequestMapping(value="/comments", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(key="#comment.getCommentId()")
    public void updateComment(@RequestBody @Valid Comment comment) {
        dao.updateComment(comment);
    }


    @RequestMapping(value="/comments/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict
    public void deleteComment(@PathVariable int id) {
        dao.deleteComment(id);
    }


}
