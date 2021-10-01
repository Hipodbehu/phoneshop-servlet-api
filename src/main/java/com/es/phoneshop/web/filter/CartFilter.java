package com.es.phoneshop.web.filter;

import com.es.phoneshop.model.service.CartService;
import com.es.phoneshop.model.service.DefaultCartService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CartFilter implements Filter {
  private CartService cartService;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    this.cartService = DefaultCartService.getInstance();
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    servletRequest.setAttribute("cart", cartService.getCart((HttpServletRequest) servletRequest));
    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {

  }
}
