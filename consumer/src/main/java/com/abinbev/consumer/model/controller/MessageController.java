package com.abinbev.consumer.model.controller;

import com.abinbev.consumer.model.Message;
import com.abinbev.consumer.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.GenericDomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.MetaData;
import org.axonframework.serialization.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@Slf4j
public class MessageController {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private MessageService consumerService;

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessage(){

        return new ResponseEntity(consumerService.findMessages(), HttpStatus.OK);
    }

    @GetMapping("/events/{aggregateId}")
    public ResponseEntity<List<Object>> listEvents(@PathVariable String aggregateId) {
        log.info("GET /events/{aggregateId}");

        eventStore.readEvents(aggregateId);

        /*eventStore.readEvents(aggregateId).asStream().forEach(i ->{
            System.out.println(i);
            i.getPayload();
            i.getMetaData();
        });*/

        return ResponseEntity.ok().body(eventStore.readEvents(aggregateId)
                .asStream()
                .map( s ->getStringObjectMap(s))
                .collect(Collectors.toList()));
    }

    private Map<String, Object> getStringObjectMap(org.axonframework.eventhandling.DomainEventMessage<?> s) {


        Map<String, Object> eventSourcing = new HashMap<>();
        eventSourcing.put("payload", s.getPayload());
        eventSourcing.put("timestamp", s.getTimestamp().getEpochSecond());
        eventSourcing.put("aggregateIdentifier", s.getAggregateIdentifier());
        eventSourcing.put("identifier", s.getIdentifier());
        eventSourcing.put("type", s.getType());
        eventSourcing.put("payloadType", s.getPayloadType().getCanonicalName());
        try {
            eventSourcing.put("metadata", ((GenericDomainEventMessage) s).getMetaData());
            //eventSourcing.put("metadata", s.getMetaData());

            //s.getMetaData().entrySet().forEach(entry ->{
            //    log.info("Key: " + entry.getKey() + " value" + entry.getValue());
            //});
            //if(!s.getMetaData().entrySet().isEmpty()) {
            //    eventSourcing.put("metadata", s.getMetaData());
            //}

        }catch (Exception e){
            log.error(e.getMessage());
        }

        return eventSourcing;
    }
}
