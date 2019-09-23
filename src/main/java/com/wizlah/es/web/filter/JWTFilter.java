package com.wizlah.es.web.filter;

import com.wizlah.es.commons.TokenUrlFilter;
import com.wizlah.es.commons.TokenUtils;
import com.wizlah.es.commons.UserToken;
import com.wizlah.es.config.security.FilterUrl;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JWTFilter extends BasicAuthenticationFilter {

  @Autowired private TokenUtils tokenUtils;
  @Autowired private FilterUrl filterUrl;

  public JWTFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String requestURI = request.getRequestURI();
    String method = request.getMethod();
    if (TokenUrlFilter.filterUrl(filterUrl.getUrls(), requestURI, method)) {
      SecurityContextHolder.getContext()
          .setAuthentication(
              new UsernamePasswordAuthenticationToken(
                  new UserToken("whizhome", "0"),
                  "whizhome",
                  AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_GUEST")));
      chain.doFilter(request, response);
    } else {
      Authentication authentication = tokenUtils.getAuthentication(request, response);
      if (authentication != null) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Claims parseJWT =
            tokenUtils.parseJWT(
                request.getHeader(TokenUtils.HEADER_STRING).replace(TokenUtils.TOKEN_PREFIX, ""));
        UserToken userToken = (UserToken) authentication.getPrincipal();
        request.setAttribute("userId", userToken.getUserId());
        request.setAttribute("roleAuthorities", parseJWT.get("authorities"));
        request.setAttribute("username", userToken.getUsername());
        chain.doFilter(request, response);
      }
    }
  }
}
