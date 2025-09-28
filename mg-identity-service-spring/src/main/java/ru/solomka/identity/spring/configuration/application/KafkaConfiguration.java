package ru.solomka.identity.spring.configuration.application;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.solomka.identity.kafka.KafkaNotificationAdapter;
import ru.solomka.identity.kafka.event.KafkaUserCreatedEvent;
import ru.solomka.identity.kafka.event.KafkaUserDeletedEvent;
import ru.solomka.identity.kafka.event.KafkaUserUpdatedEvent;

@Configuration
public class KafkaConfiguration {

    @Bean
    public KafkaTemplate<String, KafkaUserCreatedEvent> userCreatedEventKafkaTemplate(@NonNull ProducerFactory<String, KafkaUserCreatedEvent> factory) {
        return new KafkaTemplate<>(factory);
    }


    @Bean
    public KafkaTemplate<String, KafkaUserUpdatedEvent> userUpdatedEventKafkaTemplate(@NonNull ProducerFactory<String, KafkaUserUpdatedEvent> factory) {
        return new KafkaTemplate<>(factory);
    }

    @Bean
    public KafkaTemplate<String, KafkaUserDeletedEvent> userDeletedEventKafkaTemplate(@NonNull ProducerFactory<String, KafkaUserDeletedEvent> factory) {
        return new KafkaTemplate<>(factory);
    }

    @Bean
    KafkaNotificationAdapter entityPrincipalEntityEntityNotification(@NonNull KafkaTemplate<String, KafkaUserCreatedEvent> userCreatedEventKafkaTemplate,
                                                                     @NonNull KafkaTemplate<String, KafkaUserUpdatedEvent> userUpdatedEventKafkaTemplate,
                                                                     @NonNull KafkaTemplate<String, KafkaUserDeletedEvent> userDeletedEventKafkaTemplate) {
        return new KafkaNotificationAdapter(userCreatedEventKafkaTemplate, userUpdatedEventKafkaTemplate, userDeletedEventKafkaTemplate);
    }
}

