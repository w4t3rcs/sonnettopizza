package org.sonnetto.notification.sender.impl;

import lombok.RequiredArgsConstructor;
import org.sonnetto.notification.document.Message;
import org.sonnetto.notification.sender.MessageSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSender implements MessageSender {
    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(Message message) {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo(message.getTarget());
        simpleMessage.setSubject(message.getSubject());
        simpleMessage.setText(message.getBody());
        mailSender.send(simpleMessage);
    }
}
