package com.wizlah.es.daos;

import com.wizlah.es.entity.AccountEntity;

import java.util.Optional;

public interface IAccountDao {

    void save(AccountEntity accountEntity);

    void delete(AccountEntity accountEntity);

    Optional <AccountEntity> findById(long id);

    Optional<AccountEntity> findByPhone(String phone);
}
