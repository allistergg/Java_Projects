package com.trilogyed.stwitter.service;

import com.trilogyed.stwitter.model.Comment;
import com.trilogyed.stwitter.model.Post;
import com.trilogyed.stwitter.util.feign.CommentClient;
import com.trilogyed.stwitter.util.feign.PostClient;
import com.trilogyed.stwitter.viewmodel.PostViewModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceLayer {

    PostClient postClient;

    CommentClient commentClient;

    RabbitTemplate rabbitTemplate;

    @Autowired
    public ServiceLayer(PostClient postClient, CommentClient commentClient, RabbitTemplate rabbitTemplate) {
        this.postClient = postClient;
        this.commentClient = commentClient;
        this.rabbitTemplate = rabbitTemplate;

    }

    public static final String EXCHANGE = "comment-exchange";
    public static final String ROUTING_KEY = "comment.create.service";

    public PostViewModel createPost(PostViewModel pvm) {

        Post post = new Post(pvm.getPostDate(), pvm.getPosterName(), pvm.getPost());
        return buildPVM(postClient.createPost(post));

    }

    public Optional<PostViewModel> findPostById(int id) {
        return postClient.getPost(id)
                .map(this::buildPVM);
    }

    public List<PostViewModel> findAllPosts() {
        return postClient.getAllPosts()
                .stream()
                .map(this::buildPVM)
                .collect(Collectors.toList());
    }

    public void createComment(Comment comment) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, comment);
    }

    private PostViewModel buildPVM(Post post) {
        return new PostViewModel(post.getPostId(), post.getPostDate(), post.getPosterName(),
                post.getPost(), getCommentsByPost(post.getPostId()));
    }

    private List<Comment> getCommentsByPost(int id) {
        return commentClient.getCommentsByPost(id);
    }





}
