package org.core_service.producer;

import org.core_service.entity.KafkaEventGetImj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerGetImg extends
        AbstractKafkaProducer<KafkaEventGetImj> {

    @Value(value = "${kafka.topics.get_imj}")
    private String topicGetImj;

    @Autowired
    public KafkaProducerGetImg(KafkaTemplate<String, String> kafkaTemplate) {
        super(kafkaTemplate);
    }

    public void sendMessage(KafkaEventGetImj kafkaEventGetImj) {
        sendMessage(kafkaEventGetImj, topicGetImj);
    }
}
