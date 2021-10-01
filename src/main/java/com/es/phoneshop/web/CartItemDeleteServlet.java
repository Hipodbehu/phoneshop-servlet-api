package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.service.CartService;
import com.es.phoneshop.model.service.DefaultCartService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartItemDeleteServlet extends HttpServlet {
  public static final String SUCCESS_MESSAGE = "Deleted successfully";
  private CartService cartService;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    this.cartService = DefaultCartService.getInstance();
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String cartItemId = request.getPathInfo().substring(1);
    Cart cart = cartService.getCart(request);
    cartService.delete(cart, Long.parseLong(cartItemId));
    response.sendRedirect(request.getContextPath() + "/cart" + "?message=" + SUCCESS_MESSAGE);
  }
}
