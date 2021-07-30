package com.abinbev.consumer.model.repository;

import com.abinbev.consumer.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    static HashMap<String,Message> orderStore = new HashMap<>();

    public default void storeOrder(Message message) {
        orderStore.put(message.getId(), message);
        System.out.println("order added to repository:::: Current map values ");
        orderStore.forEach((k,v) -> System.out.println(v));
    }

}
