    package com.trilogyed.stwitter.controller;

    import com.trilogyed.stwitter.model.Comment;
    import com.trilogyed.stwitter.service.ServiceLayer;
    import com.trilogyed.stwitter.viewmodel.PostViewModel;
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

@RestController
@RequestMapping("/")
@CacheConfig(cacheNames={"pvms"})
public class StwitterController {

    @Autowired
    private ServiceLayer service;

//    public StwitterController(ServiceLayer service) {
//        this.service = service;
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CachePut(key="#result.getPostId()")
    public PostViewModel createPost(@RequestBody @Valid PostViewModel pvm) {
        System.out.println(pvm.toString());

        return service.createPost(pvm);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Cacheable
    public PostViewModel getPostById(@PathVariable int id) {
        return service.findPostById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostViewModel> getAllPosts() {
        return service.findAllPosts();
    }

    @PostMapping("/comment")
    @ResponseStatus(HttpStatus.OK)
    @CacheEvict(key="#comment.getPostId()")
    public void createComment(@RequestBody @Valid Comment comment) {
        System.out.println(comment);
        service.createComment(comment);
    }
}
