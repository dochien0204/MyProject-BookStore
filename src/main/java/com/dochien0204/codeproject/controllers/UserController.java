package com.dochien0204.codeproject.controllers;

import com.dochien0204.codeproject.base.RestApiV1;
import com.dochien0204.codeproject.base.VsResponseUtil;
import com.dochien0204.codeproject.contants.UrlConstant;
import com.dochien0204.codeproject.dtos.user.*;
import com.dochien0204.codeproject.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;

import java.util.List;
import java.util.stream.Collectors;

@RestApiV1
public class UserController {

  private final UserService userService;

  private final ModelMapper modelMapper;


  public UserController(UserService userService, ModelMapper modelMapper) {
    this.userService = userService;
    this.modelMapper = modelMapper;
  }

  //Lấy ra thông tin danh sách tất cả user
  @GetMapping(UrlConstant.User.LIST)
  public ResponseEntity<?> findAllUser() {
    List<GetListUserItemDTO> output = userService.findAllUser().stream().map(item -> modelMapper.map(item, GetListUserItemDTO.class)).collect(Collectors.toList());
    return VsResponseUtil.ok(output);
  }

  //lấy ra user theo userId
  @GetMapping(UrlConstant.User.GET)
  public ResponseEntity<?> findUserById(@PathVariable Integer userId) {
    GetUserByIdDTO output = modelMapper.map(userService.findUserById(userId), GetUserByIdDTO.class);
    return VsResponseUtil.ok(output);
  }

  //Lấy ra user theo userName
  @GetMapping(UrlConstant.User.GET_BY_USERNAME)
  public ResponseEntity<?> findUserByUserName(@PathVariable String userName) {
    GetUserByUserNameDTO output = modelMapper.map(userService.findUserByUserName(userName), GetUserByUserNameDTO.class);
    return VsResponseUtil.ok(output);
  }

  //Lấy ra user theo email
  @GetMapping(UrlConstant.User.GET_BY_EMAIL)
  public ResponseEntity<?> findUserByEmail(@PathVariable String email) {
    GetUserByEmailDTO output = modelMapper.map(userService.findUserByEmail(email), GetUserByEmailDTO.class);
    return VsResponseUtil.ok(output);
  }

  //Lấy ra user theo sub string
  @GetMapping(UrlConstant.User.GET_BY_SUBNAME)
  public ResponseEntity<?> findBySubName(@RequestPart(name = "sub-name") String subName) {
    List<GetUserItemBySubNameDTO> output = userService.findUserBySubName(subName).stream().map(item -> modelMapper.map(item, GetUserItemBySubNameDTO.class)).collect(Collectors.toList());
    return VsResponseUtil.ok(output);
  }

}
