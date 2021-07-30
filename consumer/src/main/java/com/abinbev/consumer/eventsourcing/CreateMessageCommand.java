package com.abinbev.consumer.eventsourcing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
public class CreateMessageCommand {

    @TargetAggregateIdentifier
    private String id;

    private String text;

    private String from;

    private Long timestamp;
}
