package com.trilogyed.commentqueueconsumer.util.feign;

import com.trilogyed.commentqueueconsumer.model.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("comment-service")
public interface CommentClient {

    @RequestMapping(value="/comments", method= RequestMethod.POST)
    public void createComment(@RequestBody Comment comment);
}
