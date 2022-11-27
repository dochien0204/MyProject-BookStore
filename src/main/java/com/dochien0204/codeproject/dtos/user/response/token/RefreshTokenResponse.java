package com.dochien0204.codeproject.dtos.user.response.token;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenResponse {

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String access_token;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String refresh_token;
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String errorMessage;

  public RefreshTokenResponse(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public RefreshTokenResponse(String access_token, String refresh_token) {
    this.access_token = access_token;
    this.refresh_token = refresh_token;
  }
}
