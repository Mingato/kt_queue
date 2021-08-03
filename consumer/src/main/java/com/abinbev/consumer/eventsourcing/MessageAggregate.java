package com.abinbev.consumer.eventsourcing;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.GenericEventMessage;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.MetaData;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashMap;
import java.util.Map;

@Aggregate
@Getter
@Setter
@Slf4j
public class MessageAggregate {

    @AggregateIdentifier
    private String id;

    private String text;

    private String from;

    private Long timestamp;

    @CommandHandler
    public MessageAggregate(CreateMessageCommand command) {
        this.id = command.getId();
        log.info("MessageAggregate id: " + command.getId());
        CreateMessageCommand event = new CreateMessageCommand(command.getId(),
                command.getText(), command.getFrom(), command.getTimestamp());

        MetaData metaData = MetaData.with("id", "1234")
                                    .and("username", "Gunter")
                                    .and("userType", "dev");

        AggregateLifecycle.apply(event, metaData);
    }

    @EventSourcingHandler
    public void on(CreateMessageEvent event) {
        this.id = event.getId();
        this.text = event.getText();
        this.from = event.getFrom();
        this.timestamp = event.getTimestamp();
    }

}
