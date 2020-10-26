package com.bridgingcode.springbootsqssnsdemo.listener;

import com.bridgingcode.springbootsqssnsdemo.model.Names;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageListener.class);

    @Autowired
    private NotificationMessagingTemplate notificationMessagingTemplate;

    @SqsListener(value = "bridgingcode-queue", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void messageListener(Names names) {
        try {
            LOGGER.info("List of names received. Sorting...");
            notificationMessagingTemplate.sendNotification("bridgingcode-notifications",
                    "List of names received. " + Arrays.toString(names.getNames()),
                    "Names notification");

            Arrays.sort(names.getNames());
            names.setUpdatedAt(LocalDateTime.now());

            LOGGER.info("Sorted. {}", Arrays.toString(names.getNames()));
            notificationMessagingTemplate.sendNotification("bridgingcode-notifications",
                    "List of names sorted. " + Arrays.toString(names.getNames()),
                    "Names notification");
        } catch (Exception e) {
            LOGGER.error("An error occurred.", e);
        }
    }
}
