package ru.cinimex.notification.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    private final String senderEmail = "robot@mail.ru";

    @BeforeEach
    void setUp() {
        // Пробрасываем значение @Value вручную
        ReflectionTestUtils.setField(emailService, "fromEmail", senderEmail);
    }

    @Test
    @DisplayName("Корректное формирование и отправка SimpleMailMessage")
    void shouldCreateCorrectMailMessage() {
        String to = "user@gmail.com";
        String subject = "Hello";
        String text = "Welcome to our service";

        emailService.sendEmail(to, subject, text);

        // Используем ArgumentCaptor, чтобы проверить поля созданного внутри сообщения
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(messageCaptor.capture());

        SimpleMailMessage capturedMessage = messageCaptor.getValue();

        assertEquals(senderEmail, capturedMessage.getFrom());
        assertEquals(to, capturedMessage.getTo()[0]);
        assertEquals(subject, capturedMessage.getSubject());
        assertEquals(text, capturedMessage.getText());
    }
}