package com.dochien0204.codeproject.entities.base;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AbstractAuditingEntity implements Serializable {

  @JsonProperty("createdBy")
  private String createdBy;

  @JsonProperty("createdDate")
  private Long createdDate;

  @JsonProperty("lastModifiedBy")
  private String lastModifiedBy;

  @JsonProperty("lastModifiedDate")
  private Long lastModifiedDate;

  @JsonProperty("activeFlag")
  private boolean activeFlag = true;

  @JsonProperty("deleteFlag")
  private boolean deleteFlag = false;
}
