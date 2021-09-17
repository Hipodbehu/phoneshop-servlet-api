package com.es.phoneshop.model.service;

import com.es.phoneshop.exception.BadQuantityException;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;

public interface CartService {
  Cart getCart();
  void add(Cart cart, Long productId, int quantity) throws OutOfStockException, BadQuantityException;
}
