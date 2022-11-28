package com.dochien0204.codeproject.entities.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class AbstractAuditingEntity implements Serializable {

  private String createdBy;

  private Long createdDate = System.currentTimeMillis();

  private String lastModifiedBy;

  private Long lastModifiedDate = System.currentTimeMillis();

  private boolean activeFlag = true;

  private boolean deleteFlag = false;
}
