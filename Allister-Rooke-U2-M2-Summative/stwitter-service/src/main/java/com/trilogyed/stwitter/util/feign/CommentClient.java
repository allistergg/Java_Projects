package com.trilogyed.stwitter.util.feign;

import com.trilogyed.stwitter.model.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("comment-service")
public interface CommentClient {
    @RequestMapping(value="/posts/{id}/comments", method= RequestMethod.GET)
    public List<Comment> getCommentsByPost(@PathVariable Integer id);

    @GetMapping("/comments")
    public List<Comment> getAllComments();

    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable Integer id);

    @PostMapping("/comments")
    public Comment createComment(@RequestBody Comment comment);

    @GetMapping("/comments/{id}")
    public Comment getComment(@PathVariable Integer id);
}