package com.wizlah.es.daos;

import com.wizlah.es.commons.Role;
import com.wizlah.es.entity.RoleEntity;

import java.util.Optional;

public interface IRoleDao {

  void save(RoleEntity roleEntity);

  void delete(RoleEntity roleEntity);

  Iterable<RoleEntity> findAll();

  Optional<RoleEntity> findByRole(Role role);
}
