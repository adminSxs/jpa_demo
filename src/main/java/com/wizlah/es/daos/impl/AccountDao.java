package com.wizlah.es.daos.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wizlah.es.daos.IAccountDao;
import com.wizlah.es.entity.AccountEntity;
import com.wizlah.es.entity.QAccountEntity;
import com.wizlah.es.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class AccountDao implements IAccountDao {

  @Autowired private AccountRepository accountRepository;
  @Autowired private EntityManager entityManager;
  @Autowired private QAccountEntity qAccount;

  private JPAQueryFactory queryFactory;
  @PostConstruct
  public void setup(){
    queryFactory=new JPAQueryFactory(entityManager);
  }

  @Override
  public void save(AccountEntity accountEntity) {
    accountRepository.saveAndFlush(accountEntity);
  }

  @Override
  public void delete(AccountEntity accountEntity) {
    accountRepository.delete(accountEntity);
  }

  @Override
  public Optional<AccountEntity> findById(long id) {
    return accountRepository.findById(id);
  }

  @Override
  public Optional<AccountEntity> findByPhone(String phone) {
    return accountRepository.findOne(qAccount.phone.eq(phone));
  }
}
