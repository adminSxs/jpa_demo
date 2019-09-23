package com.wizlah.es.commons;

import org.springframework.security.access.AccessDeniedException;

public class ServiceException extends AccessDeniedException {
  private static final long serialVersionUID = -3395290799475016249L;

  private ErrorCode code = ErrorCode.SUCCESS;

  public ServiceException(ErrorCode code) {
    super(code.getMessage());
    this.code = code;
  }

  public ErrorCode getErrorCode() {
    return this.code;
  }

  public static void capture(ErrorCode errorCode) {
    throw new ServiceException(errorCode);
  }
}
