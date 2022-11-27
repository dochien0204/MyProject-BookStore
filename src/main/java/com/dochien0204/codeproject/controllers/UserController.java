package com.dochien0204.codeproject.controllers;

import com.dochien0204.codeproject.base.RestApiV1;
import com.dochien0204.codeproject.base.VsResponseUtil;
import com.dochien0204.codeproject.contants.UrlConstant;
import com.dochien0204.codeproject.dtos.user.*;
import com.dochien0204.codeproject.dtos.user.response.token.RefreshTokenResponse;
import com.dochien0204.codeproject.dtos.user.response.user.CreateUserResponse;
import com.dochien0204.codeproject.dtos.user.response.user.DeleteUserResponse;
import com.dochien0204.codeproject.dtos.user.response.user.UpdateUserResponse;
import com.dochien0204.codeproject.services.RefreshTokenService;
import com.dochien0204.codeproject.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestApiV1
@EnableMethodSecurity
public class UserController {

  private final UserService userService;

  private final ModelMapper modelMapper;

  private final RefreshTokenService refreshTokenService;

  public UserController(UserService userService, ModelMapper modelMapper, RefreshTokenService refreshTokenService) {
    this.userService = userService;
    this.modelMapper = modelMapper;
    this.refreshTokenService = refreshTokenService;
  }

  //Lấy ra thông tin danh sách tất cả user
  @PreAuthorize("hasRole('ROLE_ADMIN')")
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

  @PermitAll
  @PostMapping(UrlConstant.User.CREATE)
  public ResponseEntity<?> createNewUser(@ModelAttribute CreateUserDTO userDTO) throws IOException {
    CreateUserResponse response = userService.save(userDTO) ? new CreateUserResponse("Created successfully") : new CreateUserResponse("Created Failed because username is exists");
    return VsResponseUtil.ok(response);
  }

  @PostMapping(UrlConstant.User.REFRESH_TOKEN)
  public ResponseEntity<?> getNewAccessToken(@RequestPart(name = "refresh_token") String refresh_token) {
    RefreshTokenResponse output = refreshTokenService.refreshAccessToken(refresh_token);
    return VsResponseUtil.ok(output);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping(UrlConstant.User.UPDATE)
  public ResponseEntity<?> updateUserByUserName(@ModelAttribute UpdateUserDTO userDTO,
                                                @PathVariable(name = "userId") Integer userId) throws IOException {
    UpdateUserResponse output = userService.update(userDTO , userId) ? new UpdateUserResponse("Updated sucessfully") : new UpdateUserResponse("Update failed because user isn't exists");
    return VsResponseUtil.ok(output);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping (UrlConstant.User.DELETE)
  public ResponseEntity<?> deleteUserById(@PathVariable(name = "userId") Integer userId) {
    DeleteUserResponse output = userService.delete(userId) ? new DeleteUserResponse("Deleted Successfully") : new DeleteUserResponse("Deleted Failed because user isn't exists");
    return VsResponseUtil.ok(output);
  }

}
