package com.trilogyed.stwitter.util.feign;


import com.trilogyed.stwitter.model.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name="post-service",decode404=true)
public interface PostClient {

    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public Post createPost(@RequestBody Post post);

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public Optional<Post> getPost(@PathVariable Integer id);

    @RequestMapping(value="/posts/", method=RequestMethod.GET)
    public List<Post> getAllPosts();

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable Integer id);

}
