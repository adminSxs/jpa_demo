package com.wizlah.es.config.security;

import com.wizlah.es.web.filter.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private SecurityHandler securityHandler;

  @Bean
  public JWTFilter authentication() throws Exception {
    return new JWTFilter(authenticationManager());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // 添加过滤器
    http.addFilterBefore(authentication(), UsernamePasswordAuthenticationFilter.class);
    // 防止csrf循环定向
    http.csrf().disable();
    // 禁用session
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    // 禁用缓存
    http.headers().cacheControl();
    // 自定义无权限返回值
    http.exceptionHandling().accessDeniedHandler(securityHandler);
  }
}
