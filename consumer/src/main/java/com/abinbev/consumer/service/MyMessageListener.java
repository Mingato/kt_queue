package com.abinbev.consumer.service;

import com.abinbev.consumer.config.RabbitMQConfig;
import com.abinbev.consumer.eventsourcing.CreateMessageCommand;
import com.abinbev.consumer.model.Message;
import com.abinbev.consumer.model.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyMessageListener{

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private CommandGateway commandGateway;

    @RabbitListener(queues = RabbitMQConfig.queueName)
    public void listener(Message message) {
        log.info("Consumer: " + message);

        message.setId(null);
        message = messageRepository.save(message);

        simpMessagingTemplate.convertAndSend("/queue/chat", message);

        log.info("Message sent! id: " + message.getId());

        CreateMessageCommand command = new CreateMessageCommand(message.getId(),
                message.getText(), message.getFrom(), System.currentTimeMillis());
        commandGateway.send(command);
    }
}