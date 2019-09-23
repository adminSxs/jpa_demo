package com.wizlah.es.commons;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class UserToken implements Serializable {
  private static final long serialVersionUID = 1L;
  public String username;
  public String userId;

  public UserToken(String username, String userId) {
    this.username = username;
    this.userId = userId;
  }
}
