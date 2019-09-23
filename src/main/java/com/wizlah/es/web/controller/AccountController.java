package com.wizlah.es.web.controller;

import com.wizlah.es.commons.JsonResponse;
import com.wizlah.es.commons.ValidateManager;
import com.wizlah.es.services.IAccountService;
import com.wizlah.es.web.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/account-api/accounts", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AccountController {
  @Autowired private ValidateManager validateManager;
  @Autowired private IAccountService accountService;

  @PostMapping
  public String register(@RequestBody AccountDTO accountDto) {
    JsonResponse jsonResponse = new JsonResponse();
    if (!validateManager.validate(jsonResponse, accountDto, "phone", "password", "avatar","role"))
      return jsonResponse.toString();
    jsonResponse.setData(accountService.register(accountDto));
    return jsonResponse.toString();
  }

  @PostMapping(value = "login")
  public String login(@RequestBody AccountDTO accountDto) {
    JsonResponse jsonResponse = new JsonResponse();
    if (!validateManager.validate(jsonResponse, accountDto, "phone", "password"))
      return jsonResponse.toString();
    jsonResponse.setData(accountService.login(accountDto));
    return jsonResponse.toString();
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping(value = "all")
  public String findAllAccount() {
    JsonResponse jsonResponse = new JsonResponse();

    return jsonResponse.toString();
  }
}
