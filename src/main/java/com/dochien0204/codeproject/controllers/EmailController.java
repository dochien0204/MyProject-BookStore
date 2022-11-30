package com.dochien0204.codeproject.controllers;

import com.dochien0204.codeproject.base.RestApiV1;
import com.dochien0204.codeproject.base.VsResponseUtil;
import com.dochien0204.codeproject.contants.UrlConstant;
import com.dochien0204.codeproject.dtos.emails.SimpleMailMessageDTO;
import com.dochien0204.codeproject.dtos.emails.SimpleMessageForAllDTO;
import com.dochien0204.codeproject.services.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiV1
public class EmailController {

  private final MailService mailService;

  public EmailController(MailService mailService) {
    this.mailService = mailService;
  }

  @PostMapping("/email/send")
  public ResponseEntity<?> sendSimpleMessage(@RequestBody SimpleMailMessageDTO message) {
    mailService.sendSimpleMessage(message);
    return VsResponseUtil.ok(message);
  }

  @PostMapping(UrlConstant.Email.SEND_SIMPLE_TO_ALL)
  public ResponseEntity<?> sendSimpleMessageToAll(@RequestBody SimpleMessageForAllDTO message) {
    mailService.sendSimpleMessageForAllUser(message);
    return VsResponseUtil.ok(message);
  }
}
