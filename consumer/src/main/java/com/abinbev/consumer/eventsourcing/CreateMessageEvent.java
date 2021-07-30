package com.abinbev.consumer.eventsourcing;

import lombok.Value;

@Value
public class CreateMessageEvent {

    private final String id;

    private final String text;

    private final String from;

    private final Long timestamp;
}
