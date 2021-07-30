package com.abinbev.consumer.eventsourcing;

import com.abinbev.consumer.model.Message;
import com.abinbev.consumer.model.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class MessageQueryHandler {

    @Autowired
    private MessageRepository orderRepository;

    @EventHandler
    public void on(CreateMessageEvent event) {
        orderRepository.storeOrder(new Message(event.getId(),
                event.getText(), event.getFrom(), event.getTimestamp()));
    }
}
