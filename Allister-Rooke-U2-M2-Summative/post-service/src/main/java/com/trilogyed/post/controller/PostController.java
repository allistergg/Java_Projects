package com.trilogyed.post.controller;

import com.trilogyed.post.dao.PostDao;
import com.trilogyed.post.model.Post;
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
@CacheConfig(cacheNames={"posts"})
public class PostController {

    @Autowired
    PostDao dao;

    @PostMapping("/posts")
    @CachePut(key="#result.getPostId()")
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@RequestBody @Valid Post post) {
        System.out.println(post);
        return dao.createPost(post);
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    @Cacheable
    public Post getPost(@PathVariable int id) {
        return Optional.ofNullable(dao.getPost(id)).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND,"Post not found with id " + id));
    }

    @RequestMapping(value="/posts", method=RequestMethod.GET)
    public List<Post> getAllPosts(){
        return dao.getAllPosts();
    }

    @RequestMapping(value = "/posts", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(key="#post.getPostId()")
    public void updatePost(@RequestBody Post post   ) {
        dao.updatePost(post);
    }

    @CacheEvict
    @RequestMapping(value = "/posts/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable int id) {
        dao.deletePost(id);
    }


}
