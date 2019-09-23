package com.wizlah.es.commons;

public enum ErrorCode {
  // parameter verification
  PARAM_INVALID(-1, "invalid parameters"),
  // common
  OUTPUT_STREAM(10000, "output stream error"),
  TOKEN_INVALID(10001, "token invalid"),
  PERMISSION_DENIED(10002, "permission denied"),
  RECORD_EXISTS(10003,"record exists"),
  RECORD_NOT_EXISTS(10004,"record not exists"),
  //account
  PHONE_EXISTS(20000,"phone exists"),
  ROLE_NOT_EXISTS(20001,"role not exists"),

  // success request
  SUCCESS(0, "success");
  private int code;
  private String msg;

  ErrorCode(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public String getMessage() {
    return msg;
  }

  public Integer getCode() {
    return code;
  }
}
