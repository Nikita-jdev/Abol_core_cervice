package org.core_service.producer;

import org.core_service.entity.KafkaEventAddImj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerAddImj extends
        AbstractKafkaProducer<KafkaEventAddImj> {

    @Value(value = "${kafka.topics.add_imj}")
    private String topicAddImj;

    @Autowired
    public KafkaProducerAddImj(KafkaTemplate<String, String> kafkaTemplate) {
        super(kafkaTemplate);
    }

    public void sendMessage(KafkaEventAddImj kafkaEventAddImj) {
        sendMessage(kafkaEventAddImj, topicAddImj);
    }
}
