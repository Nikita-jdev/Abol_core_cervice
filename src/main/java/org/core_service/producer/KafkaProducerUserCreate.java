package org.core_service.producer;

import org.core_service.entity.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerUserCreate
        extends AbstractKafkaProducer<UserDto> {

    @Value(value = "${kafka.topics.create_account}")
    private String topicCreateAccount;

    @Autowired
    public KafkaProducerUserCreate(KafkaTemplate<String, String> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Async("executor")
    public void sendMessage(UserDto userDto) {
        sendMessage(userDto, topicCreateAccount);
    }
}
