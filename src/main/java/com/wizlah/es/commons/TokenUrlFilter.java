package com.wizlah.es.commons;

import com.google.common.base.Objects;
import org.springframework.web.util.UriTemplate;

import java.util.List;

public class TokenUrlFilter {
  // 过滤无需token验证的url
  public static boolean filterUrl(List<String> urls, String requestURI, String method) {
    for (int i = 0; i < urls.size(); i++) {
      String[] urlAndMethod = urls.get(i).split(":");
      UriTemplate uriTemplate = new UriTemplate(urlAndMethod[0]);
      if (urlAndMethod.length == 1 && requestURI.startsWith(urlAndMethod[0])) {
        return true;
      }
      if (urlAndMethod.length == 2
          && uriTemplate.matches(requestURI)
          && Objects.equal(method, urlAndMethod[1])) {
        return true;
      }
    }
    return false;
  }
}
