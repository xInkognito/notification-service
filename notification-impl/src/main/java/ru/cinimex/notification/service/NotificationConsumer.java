package ru.cinimex.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.cinimex.notification.dto.NotificationMessage;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "${app.kafka.topic}", groupId = "notification-group")
    public void consume(NotificationMessage message) {
        log.info("Received message from Kafka for email: {}", message.getEmail());

        try {
            emailService.sendEmail(
                    message.getEmail(),
                    message.getHeader(),
                    message.getBody()
            );
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", message.getEmail(), e.getMessage());
        }
    }
}
