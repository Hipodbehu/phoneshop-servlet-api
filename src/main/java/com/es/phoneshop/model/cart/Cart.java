package com.es.phoneshop.model.cart;

import com.es.phoneshop.dao.AbstractEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart extends AbstractEntity implements Serializable {
  private List<CartItem> cartItemList;
  private BigDecimal totalCost;
  private int totalQuantity;

  public Cart() {
    this.cartItemList = new ArrayList<>();
  }

  public List<CartItem> getCartItemList() {
    return cartItemList;
  }

  public void setCartItemList(List<CartItem> cartItemList) {
    this.cartItemList = cartItemList;
  }

  public BigDecimal getTotalCost() {
    return totalCost;
  }


  public void setTotalCost(BigDecimal totalCost) {
    this.totalCost = totalCost;
  }

  public int getTotalQuantity() {
    return totalQuantity;
  }

  public void setTotalQuantity(int totalQuantity) {
    this.totalQuantity = totalQuantity;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("{");
    sb.append(cartItemList);
    sb.append('}');
    return sb.toString();
  }
}
