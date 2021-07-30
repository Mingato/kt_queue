package com.abinbev.producer.service;

import com.abinbev.producer.config.RabbitMQConfig;
import com.abinbev.producer.model.Message;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log
public class QueueService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public boolean push(Message message){
        message.setTimestamp(System.currentTimeMillis());

        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, RabbitMQConfig.routingkey,
                message);

        System.out.println(message);

        return true;
    }
}
