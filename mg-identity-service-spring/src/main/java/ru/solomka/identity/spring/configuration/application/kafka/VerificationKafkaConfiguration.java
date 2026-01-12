package ru.solomka.identity.spring.configuration.application.kafka;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.solomka.identity.verification.KafkaVerificationNotificationAdapter;
import ru.solomka.identity.verification.event.KafkaNotificationEvent;

@Configuration
public class VerificationKafkaConfiguration {

    @Bean
    public KafkaTemplate<String, KafkaNotificationEvent> sendNotificationEventKafkaTemplate(@NonNull ProducerFactory<String, KafkaNotificationEvent> factory) {
        return new KafkaTemplate<>(factory);
    }

    @Bean
    KafkaVerificationNotificationAdapter kafkaVerificationNotificationAdapter(@NonNull KafkaTemplate<String, KafkaNotificationEvent> kafkaNotificationEventKafkaTemplate) {
        return new KafkaVerificationNotificationAdapter(kafkaNotificationEventKafkaTemplate);
    }
}
