package com.abinbev.consumer.service;

import com.abinbev.consumer.model.Message;
import com.abinbev.consumer.model.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> findMessages(){
        return messageRepository.findAll();
    }
}
