package com.wizlah.es.entity;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "account")
public class AccountEntity extends BaseEntity {

  @Column(unique = true)
  private String phone;

  @Column private String password;
  @Column private String avatar;

  @ManyToMany
  @JoinTable(
      name = "user_role",
      inverseJoinColumns = @JoinColumn(name = "role_id"),
      joinColumns = @JoinColumn(name = "account_id"))
  private Set<RoleEntity> roles = Sets.newHashSet();
}
