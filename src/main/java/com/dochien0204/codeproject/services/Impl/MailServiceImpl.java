package com.dochien0204.codeproject.services.Impl;

import com.dochien0204.codeproject.dtos.emails.SimpleMailMessageDTO;
import com.dochien0204.codeproject.dtos.emails.SimpleMessageForAllDTO;
import com.dochien0204.codeproject.entities.User;
import com.dochien0204.codeproject.repositories.UserRepository;
import com.dochien0204.codeproject.services.MailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class MailServiceImpl implements MailService {

  private final JavaMailSender mailSender;

  private final UserRepository userRepository;

  public MailServiceImpl(JavaMailSender mailSender, UserRepository userRepository) {
    this.mailSender = mailSender;
    this.userRepository = userRepository;
  }

  public void sendSimpleMessage(SimpleMailMessageDTO mailMessageDTO) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(mailMessageDTO.getTo());
    message.setSubject(mailMessageDTO.getSubject());
    message.setText(mailMessageDTO.getText());
    mailSender.send(message);
  }

  @Override
  public void sendSimpleMailToUser(String to, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setBcc(to);
    message.setSubject(subject);
    message.setText(text);
    mailSender.send(message);
  }

  @Override
  public void sendSimpleMessageForAllUser(SimpleMessageForAllDTO messageForAllDTO) {
    Set<String> emails = new HashSet<>();
    List<User> users = userRepository.findAll();

    //gui cho nhung user dki email
    users.stream().forEach(item -> {
      if (item.getEmail() != null && item.getEmail() != "") {
        emails.add(item.getEmail());
      }
    });

    messageForAllDTO.setEmails(emails.toArray(new String[emails.size()]));
    SimpleMailMessage message = new SimpleMailMessage();
    message.setSubject(messageForAllDTO.getSubject());
    message.setText(messageForAllDTO.getText());
    message.setBcc(messageForAllDTO.getEmails());
    mailSender.send(message);
  }

  @Override
  public void sendMailToHappyBirthday(String[] emails) {
    SimpleMailMessage message = new SimpleMailMessage();
    String text = " I wish you full of happiness and love. May all your dreams turn come true and may lady luck visit you everyday. Happy birthday to one of the greatest people I've ever known.";
    String subject = "HAPPY BIRTHDAY TO YOU";
    message.setBcc(emails);
    message.setSubject(subject);
    message.setText(text);
    mailSender.send(message);
  }

}
