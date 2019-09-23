package com.wizlah.es.repositories;

import com.wizlah.es.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AccountRepository
    extends JpaRepository<AccountEntity, Long>, QuerydslPredicateExecutor<AccountEntity> {}
