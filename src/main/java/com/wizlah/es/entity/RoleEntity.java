package com.wizlah.es.entity;

import com.wizlah.es.commons.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "role")
@Setter
@Getter
public class RoleEntity extends BaseEntity {

  @Column private Role role;
}
