package com.wizlah.es.daos.impl;

import com.wizlah.es.commons.Role;
import com.wizlah.es.daos.IRoleDao;
import com.wizlah.es.entity.QRoleEntity;
import com.wizlah.es.entity.RoleEntity;
import com.wizlah.es.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleDao implements IRoleDao {

  @Autowired private RoleRepository roleRepository;
  @Autowired private QRoleEntity qRole;

  @Override
  public void save(RoleEntity roleEntity) {
    roleRepository.saveAndFlush(roleEntity);
  }

  @Override
  public void delete(RoleEntity roleEntity) {
    roleRepository.delete(roleEntity);
  }

  @Override
  public Iterable<RoleEntity> findAll() {
    return roleRepository.findAll();
  }

  @Override
  public Optional<RoleEntity> findByRole(Role role) {
    return roleRepository.findOne(qRole.role.eq(role));
  }
}
