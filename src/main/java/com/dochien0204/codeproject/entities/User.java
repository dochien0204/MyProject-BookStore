package com.dochien0204.codeproject.entities;

import com.dochien0204.codeproject.entities.base.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends AbstractAuditingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Integer userId;

  private String userName;

  private String password;

  @Nationalized
  private String address;

  private String email;

  private String phone;

  private Integer age;

  @Nationalized
  private String fullName;

  private String avatar;

  private Integer failedAttempts;

  private Boolean accountNonLocked;

  private Long lockTime;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  @JsonIgnore
  private Cart cart;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  @Fetch(value = FetchMode.SUBSELECT)
  private List<Role> roles;

  @OneToOne(mappedBy = "user")
  private PasswordResetToken passwordResetToken;

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private List<Order> orders;
}
