package org.core_service.config.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value(value = "${kafka.bootstrap_servers}")
    private String bootstrapAddress;
    @Value(value = "${kafka.topics.create_account}")
    private String topicCreateAccount;
    @Value(value = "${kafka.topics.add_imj}")
    private String topicAddImj;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topicCreateAccount() {
        return new NewTopic(topicCreateAccount, 1, (short) 1);
    }

    @Bean
    public NewTopic topicAddImj() {
        return new NewTopic(topicAddImj, 1, (short) 1);
    }
}
