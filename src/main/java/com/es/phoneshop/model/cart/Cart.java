package com.es.phoneshop.model.cart;

import java.util.ArrayList;
import java.util.List;

public class Cart {
  private List<CartItem> cartItemList;

  public Cart() {
    this.cartItemList = new ArrayList<>();
  }

  public List<CartItem> getCartItemList() {
    return cartItemList;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("{");
    sb.append(cartItemList);
    sb.append('}');
    return sb.toString();
  }
}
