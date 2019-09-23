package com.wizlah.es.initialize;

import com.wizlah.es.commons.Role;
import com.wizlah.es.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class InitializeRole implements ApplicationRunner {

  @Autowired private IRoleService roleService;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    Role[] values = Role.values();
    for (Role role : values) {
      roleService.init(role);
    }
  }
}
