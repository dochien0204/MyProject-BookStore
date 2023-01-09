package com.dochien0204.codeproject.base;

import com.dochien0204.codeproject.entities.Pagination;
import com.fasterxml.jackson.annotation.JsonInclude;

public class RestData<T> {

  private RestStatus status;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private T userMessage;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String devMessage;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private T data;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Pagination pagination;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long timeStamp;

  public RestData() {

  }

  public RestData(T data) {
    this.status = RestStatus.SUCCESS;
    this.data = data;
  }

  public RestData(T data, Pagination pagination) {
    this.status = RestStatus.SUCCESS;
    this.data = data;
    this.pagination = pagination;
  }

  public RestData(RestStatus status, T data) {
    this.status = RestStatus.SUCCESS;
    this.data = data;
  }

  public RestData(RestStatus status, T userMessage, T data) {
    this.status = RestStatus.SUCCESS;
    this.data = data;
    this.userMessage = userMessage;
  }

  public RestData(RestStatus status, T userMessage, String devMessage, T data) {
    this.status = RestStatus.SUCCESS;
    this.data = data;
    this.userMessage = userMessage;
    this.devMessage = devMessage;
  }

  public RestData(RestStatus status, T userMessage, T data, long timeStamp) {
    this.status = RestStatus.ERROR;
    this.data = data;
    this.userMessage = userMessage;
    this.timeStamp = timeStamp;
  }
  public RestData(RestStatus status, T userMessage, String devMessage, T data, Long timeStamp) {
    this.status = RestStatus.ERROR;
    this.data = data;
    this.userMessage = userMessage;
    this.devMessage = devMessage;
    this.timeStamp = timeStamp;
  }

  public static RestData<?> error(Object userMessage, String devMessage) {
    return new RestData<>(RestStatus.ERROR, userMessage, devMessage, null, System.currentTimeMillis());
  }


  public static RestData<?> error(Object userMessage) {
    return new RestData<>(RestStatus.ERROR, userMessage, null, System.currentTimeMillis());
  }

  public RestStatus getStatus() {
    return status;
  }

  public void setStatus(RestStatus status) {
    this.status = status;
  }

  public T getUserMessage() {
    return userMessage;
  }

  public void setUserMessage(T userMessage) {
    this.userMessage = userMessage;
  }

  public String getDevMessage() {
    return devMessage;
  }

  public void setDevMessage(String devMessage) {
    this.devMessage = devMessage;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public Long getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(Long timeStamp) {
    this.timeStamp = timeStamp;
  }

  public Pagination getPagination() {
    return pagination;
  }

  public void setPagination(Pagination pagination) {
    this.pagination = pagination;
  }
}
