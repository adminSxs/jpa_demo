package com.wizlah.es.services;

import com.wizlah.es.web.dto.AccountDTO;
import com.wizlah.es.web.vo.AccountVO;

public interface IAccountService {

    long register(AccountDTO accountDto);

    AccountVO login(AccountDTO accountDto);
}
