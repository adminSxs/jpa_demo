package com.wizlah.es.services.impl;

import com.wizlah.es.commons.ErrorCode;
import com.wizlah.es.commons.ServiceException;
import com.wizlah.es.daos.IAccountDao;
import com.wizlah.es.daos.IRoleDao;
import com.wizlah.es.entity.AccountEntity;
import com.wizlah.es.entity.RoleEntity;
import com.wizlah.es.mapper.AccountMapper;
import com.wizlah.es.services.IAccountService;
import com.wizlah.es.web.dto.AccountDTO;
import com.wizlah.es.web.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService implements IAccountService {
  @Autowired private IAccountDao accountDao;
  @Autowired private AccountMapper accountMapper;
  @Autowired private IRoleDao roleDao;

  @Override
  public long register(AccountDTO accountDto) {
    Optional<AccountEntity> accountOptional = accountDao.findByPhone(accountDto.getPhone());
    if (!accountOptional.isPresent()) throw new ServiceException(ErrorCode.PHONE_EXISTS);
    Optional<RoleEntity> roleOptional = roleDao.findByRole(accountDto.getRole());
    if (!roleOptional.isPresent()) throw new ServiceException(ErrorCode.ROLE_NOT_EXISTS);
    RoleEntity role = roleOptional.get();
    AccountEntity account = accountMapper.dtoToEntity(accountDto);
    account.getRoles().add(role);
    accountDao.save(account);
    return account.getId();
  }

  @Override
  public AccountVO login(AccountDTO accountDto) {

    return null;
  }
}
