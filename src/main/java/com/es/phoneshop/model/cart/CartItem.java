package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.io.Serializable;

public class CartItem implements Serializable {
  private Product product;
  private int quantity;

  public CartItem(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  public CartItem(CartItem cartItem) {
    this(cartItem.product, cartItem.quantity);
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CartItem cartItem = (CartItem) o;

    if (quantity != cartItem.quantity) return false;
    return product != null ? product.equals(cartItem.product) : cartItem.product == null;
  }

  @Override
  public int hashCode() {
    int result = product != null ? product.hashCode() : 0;
    result = 31 * result + quantity;
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder(product.getCode());
    sb.append(", ").append(quantity);
    return sb.toString();
  }
}
