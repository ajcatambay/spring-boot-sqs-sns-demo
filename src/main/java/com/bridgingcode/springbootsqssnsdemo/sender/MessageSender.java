package com.bridgingcode.springbootsqssnsdemo.sender;

import com.bridgingcode.springbootsqssnsdemo.model.Names;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class MessageSender {

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @PostMapping("/sendNames")
    public ResponseEntity<String> sendNames(@RequestBody Names names) {
        try {
            names.setCreatedAt(LocalDateTime.now());
            names.setUpdatedAt(LocalDateTime.now());

            queueMessagingTemplate.convertAndSend("bridgingcode-queue", names);

            return new ResponseEntity<>("Sent.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
