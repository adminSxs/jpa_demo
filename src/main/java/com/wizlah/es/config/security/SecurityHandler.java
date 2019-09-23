package com.wizlah.es.config.security;

import com.wizlah.es.commons.ErrorCode;
import com.wizlah.es.commons.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Configuration
public class SecurityHandler implements AccessDeniedHandler { // NO_UCD (use default)

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException, ServletException {
    log.info("unauthorized service call");
    JsonResponse jsonResponse = new JsonResponse();
    response.setContentType("application/json;charset=UTF-8");
    jsonResponse.setError(ErrorCode.PERMISSION_DENIED);
    ServletOutputStream outputStream = response.getOutputStream();
    response.setStatus(HttpServletResponse.SC_OK);
    outputStream.write(jsonResponse.toString().getBytes());
    outputStream.flush();
    outputStream.close();
  }
}
