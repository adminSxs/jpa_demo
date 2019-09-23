package com.wizlah.es.web;

import com.wizlah.es.commons.JsonResponse;
import com.wizlah.es.commons.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ServiceExceptionHandler {

  @ExceptionHandler(ServiceException.class)
  @ResponseBody
  String handleException(ServiceException e) {
    JsonResponse jsonResponse = new JsonResponse();
    jsonResponse.setError(e.getErrorCode());
    return jsonResponse.toString();
  }
}
