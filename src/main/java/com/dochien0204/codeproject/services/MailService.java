package com.dochien0204.codeproject.services;

import com.dochien0204.codeproject.dtos.emails.SimpleMailMessageDTO;
import com.dochien0204.codeproject.dtos.emails.SimpleMessageForAllDTO;

public interface MailService {
  void sendSimpleMessage(SimpleMailMessageDTO mailMessageDTO);

  void sendSimpleMailToUser(String to, String subject, String text);

  void sendSimpleMessageForAllUser(SimpleMessageForAllDTO messageForAllDTO);
}
