package com.abinbev.producer.controller;

import com.abinbev.producer.model.Message;
import com.abinbev.producer.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class MessageController {

    @Autowired
    private QueueService queueService;

    @PostMapping("/message")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message){

        if(queueService.push(message)){
            return new ResponseEntity(message, HttpStatus.OK);
        }

        return new ResponseEntity(null, HttpStatus.OK);
    }

}
