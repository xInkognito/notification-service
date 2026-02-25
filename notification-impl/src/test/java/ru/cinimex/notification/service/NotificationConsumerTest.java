package ru.cinimex.notification.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.cinimex.notification.dto.NotificationMessage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationConsumerTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private NotificationConsumer notificationConsumer;

    @Test
    @DisplayName("Успешная обработка сообщения из Kafka")
    void shouldConsumeAndSendEmail() {
        NotificationMessage message = new NotificationMessage(
                "test@mail.ru", "Заголовок", "Текст сообщения");

        notificationConsumer.consume(message);

        verify(emailService, times(1)).sendEmail(
                eq("test@mail.ru"),
                eq("Заголовок"),
                eq("Текст сообщения")
        );
    }
}