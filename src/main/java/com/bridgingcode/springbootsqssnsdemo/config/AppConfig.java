package com.bridgingcode.springbootsqssnsdemo.config;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

@Configuration
public class AppConfig {

    @Bean
    public MappingJackson2MessageConverter messageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter jackson2MessageConverter =
                new MappingJackson2MessageConverter();

        jackson2MessageConverter.setSerializedPayloadClass(String.class);
        jackson2MessageConverter.setStrictContentTypeMatch(false);
        jackson2MessageConverter.setObjectMapper(objectMapper);

        return jackson2MessageConverter;
    }

    @Bean
    public SimpleMessageListenerContainerFactory containerFactory() {
        SimpleMessageListenerContainerFactory containerFactory =
                new SimpleMessageListenerContainerFactory();

        containerFactory.setMaxNumberOfMessages(10);
        return containerFactory;
    }

    @Bean
    public AmazonSQSAsync amazonSQS() {
        AmazonSQSAsync sqsClient = AmazonSQSAsyncClientBuilder.defaultClient();

        return sqsClient;
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {
        return new QueueMessagingTemplate(amazonSQSAsync);
    }

    @Bean
    public AmazonSNS amazonSNS() {
        AmazonSNS snsClient = AmazonSNSClientBuilder.defaultClient();

        return snsClient;
    }

    @Bean
    public NotificationMessagingTemplate notificationMessagingTemplate(AmazonSNS amazonSNS) {
        return new NotificationMessagingTemplate(amazonSNS);
    }
}
