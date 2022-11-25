package com.dochien0204.codeproject.entities;

import com.dochien0204.codeproject.entities.base.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "catalog")
public class Catalog extends AbstractAuditingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "catalog_id")
  private Integer catalogId;

  private String catalogName;

  @OneToMany(mappedBy = "catalog")
  private List<Book> books;
}