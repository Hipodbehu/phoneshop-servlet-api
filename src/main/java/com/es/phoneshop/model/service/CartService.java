package com.es.phoneshop.model.service;

import com.es.phoneshop.exception.BadQuantityException;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
  Cart getCart(HttpServletRequest request);
  void add(Cart cart, Long productId, int quantity) throws OutOfStockException, BadQuantityException;
  void update(Cart cart, Long productId, int quantity) throws OutOfStockException, BadQuantityException;
  void delete(Cart cart, Long productId);
}
