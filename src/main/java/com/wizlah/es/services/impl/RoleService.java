package com.wizlah.es.services.impl;

import com.wizlah.es.commons.ErrorCode;
import com.wizlah.es.commons.Role;
import com.wizlah.es.commons.ServiceException;
import com.wizlah.es.daos.IRoleDao;
import com.wizlah.es.entity.RoleEntity;
import com.wizlah.es.mapper.RoleMapper;
import com.wizlah.es.services.IRoleService;
import com.wizlah.es.web.dto.RoleDTO;
import com.wizlah.es.web.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {
  @Autowired private IRoleDao roleDao;
  @Autowired private RoleMapper roleMapper;

  @Override
  public void init(Role role) {
    Optional<RoleEntity> roleOptional = roleDao.findByRole(role);
    if (!roleOptional.isPresent()) {
      RoleEntity roleEntity = new RoleEntity();
      roleEntity.setRole(role);
      roleDao.save(roleEntity);
    }
  }

  @Override
  public long save(RoleDTO roleDto) {
    Optional<RoleEntity> roleOptional = roleDao.findByRole(roleDto.getRole());
    if (roleOptional.isPresent()) throw new ServiceException(ErrorCode.RECORD_EXISTS);
    RoleEntity role = roleMapper.dtoToEntity(roleDto);
    roleDao.save(role);
    return role.getId();
  }

  @Override
  public void delete(long id) {}

  @Override
  public List<RoleVO> findAll() {
    return null;
  }
}
