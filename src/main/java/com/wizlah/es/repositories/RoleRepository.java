package com.wizlah.es.repositories;

import com.wizlah.es.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RoleRepository extends JpaRepository<RoleEntity,Long>, QuerydslPredicateExecutor<RoleEntity> {
}
