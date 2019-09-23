package com.wizlah.es.config;

import com.wizlah.es.entity.QAccountEntity;
import com.wizlah.es.entity.QRoleEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class QueryBean {

    @Bean
    @Lazy
    public QRoleEntity qRole(){
        return QRoleEntity.roleEntity;
    }
    @Bean
    @Lazy
    public QAccountEntity qAccount(){
        return QAccountEntity.accountEntity;
    }
}
