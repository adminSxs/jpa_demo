package com.wizlah.es.services;

import com.wizlah.es.commons.Role;
import com.wizlah.es.web.dto.RoleDTO;
import com.wizlah.es.web.vo.RoleVO;

import java.util.List;

public interface IRoleService {

  void init(Role role);

  long save(RoleDTO roleDto);

  void delete(long id);

  List<RoleVO> findAll();
}
