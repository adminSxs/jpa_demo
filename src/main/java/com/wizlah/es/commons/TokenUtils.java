package com.wizlah.es.commons;

import cn.hutool.core.codec.Base64;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TokenUtils {

  private static final long EXP = 7 * 24 * 60 * 60 * 1000; // 7天过期
  private static final String SECRETKEY = "DemoApp"; // jwt密码
  public static final String TOKEN_PREFIX = "Bearer "; // token前缀
  public static final String HEADER_STRING = "Authorization"; // 存放token的Header Key

  public Claims parseJWT(String token) {
    try {
      Claims claims =
          Jwts.parser()
              .setSigningKey(Base64.encode(SECRETKEY).getBytes())
              .parseClaimsJws(token)
              .getBody();
      return claims;
    } catch (Exception ex) {
      return null;
    }
  }

  public String createJWT(
      String phone,
      String authorities,
      String userId,
      String username,
      String avatar,
      String role) {
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);
    // 添加构成JWT的参数
    JwtBuilder builder =
        Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .claim("authorities", authorities)
            .claim("username", username)
            .claim("avatar", avatar)
            .claim("phone", phone)
            .claim("userId", userId)
            .claim("role", role)
            .signWith(signatureAlgorithm, Base64.encode(SECRETKEY).getBytes());
    // 添加Token过期时间
    if (EXP >= 0) {
      long expMillis = nowMillis + EXP;
      Date exp = new Date(expMillis);
      builder.setExpiration(exp).setNotBefore(now);
    }
    // 生成JWT
    return builder.compact();
  }

  public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    JsonResponse json = new JsonResponse();
    response.setContentType("application/json;charset=UTF-8");
    OutputStream out = response.getOutputStream();
    String token = request.getHeader(HEADER_STRING);
    if (Strings.isNullOrEmpty(token)) {
      json.setError(ErrorCode.TOKEN_INVALID);
      out.write(json.toString().getBytes());
      out.close();
      return null;
    }
    // 验证token格式
    if (!token.contains(TOKEN_PREFIX)) {
      json.setError(ErrorCode.TOKEN_INVALID);
      out.write(json.toString().getBytes());
      out.close();
      return null;
    }
    token = token.replace(TOKEN_PREFIX, "");
    // 解析token
    Claims parseJWT = parseJWT(token);
    if (Objects.equal(null, parseJWT)) {
      json.setError(ErrorCode.TOKEN_INVALID);
      out.write(json.toString().getBytes());
      out.close();
      return null;
    }
    // expired
    Date date = parseJWT.getExpiration();
    if (!date.after(new Date())) {
      json.setError(ErrorCode.TOKEN_INVALID);
      out.write(json.toString().getBytes());
      out.close();
      return null;
    }
    String userId = String.valueOf(parseJWT.get("userId"));
    String authorities = ((String) parseJWT.get("authorities"));
    String username = (String) parseJWT.get("username");
    List<GrantedAuthority> list = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(new UserToken(username, userId), null, list);
    return usernamePasswordAuthenticationToken;
  }

  private String getAuthorization(String platForm, String authorities) {
    return Arrays.stream(authorities.split(","))
        .map(
            authority -> {
              if ("ROLE_ADMIN".equals(authority)) return String.join("", platForm, "admin");
              else return authority;
            })
        .collect(Collectors.joining(","));
  }
}
