package com.es.phoneshop.web.filter;

import com.es.phoneshop.security.DefaultDosProtectionService;
import com.es.phoneshop.security.DosProtectionService;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DosFilter implements Filter {
  private DosProtectionService dosProtectionService;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    this.dosProtectionService = DefaultDosProtectionService.getInstance();
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    if (dosProtectionService.isAllowed(servletRequest.getRemoteAddr())) {
      filterChain.doFilter(servletRequest, servletResponse);
    } else {
      HttpServletResponse response = (HttpServletResponse) servletResponse;
      response.setStatus(429);
    }
  }

  @Override
  public void destroy() {

  }
}
