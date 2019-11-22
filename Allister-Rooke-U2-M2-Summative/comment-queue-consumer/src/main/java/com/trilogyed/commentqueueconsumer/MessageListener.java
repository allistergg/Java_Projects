package com.trilogyed.commentqueueconsumer;

import com.trilogyed.commentqueueconsumer.model.Comment;
import com.trilogyed.commentqueueconsumer.util.feign.CommentClient;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    @Autowired
    CommentClient client;

    @RabbitListener(queues = CommentQueueConsumerApplication.QUEUE_NAME)
    public void receiveMessage(Comment msg) {
        client.createComment(msg);
    }

}
