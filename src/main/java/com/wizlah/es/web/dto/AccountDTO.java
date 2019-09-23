package com.wizlah.es.web.dto;

import com.wizlah.es.commons.Role;
import lombok.Getter;
import lombok.Setter;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

@Setter
@Getter
public class AccountDTO {
  private String id;

  @NotEmpty(errorCode = "phone", message = "empty phone")
  @NotNull(errorCode = "phone", message = "null phone")
  private String phone;

  @NotEmpty(errorCode = "password", message = "empty password")
  @NotNull(errorCode = "password", message = "null password")
  private String password;

  @NotEmpty(errorCode = "avatar", message = "empty avatar")
  @NotNull(errorCode = "avatar", message = "null avatar")
  private String avatar;

  @NotNull(errorCode = "role", message = "null role")
  private Role role;
}
