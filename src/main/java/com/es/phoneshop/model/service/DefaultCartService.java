package com.es.phoneshop.model.service;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.BadQuantityException;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;

import java.util.concurrent.locks.ReentrantLock;

public class DefaultCartService implements CartService {
  private static volatile DefaultCartService instance;
  private ReentrantLock lock;
  private ProductDao productDao;

  private DefaultCartService() {
    this.lock = new ReentrantLock();
    this.productDao = ArrayListProductDao.getInstance();
  }

  public static DefaultCartService getInstance() {
    DefaultCartService localInstance = instance;
    if (localInstance == null) {
      synchronized (DefaultCartService.class) {
        localInstance = instance;
        if (localInstance == null) {
          localInstance = new DefaultCartService();
          instance = localInstance;
        }
      }
    }
    return localInstance;
  }

  @Override
  public Cart getCart() {
    lock.lock();
    Cart cart;
    try {
      cart = new Cart();
    } finally {
      lock.unlock();
    }
    return cart;
  }

  @Override
  public void add(Cart cart, Long productId, int quantity) throws OutOfStockException, BadQuantityException {
    if (quantity <= 0) {
      throw new BadQuantityException();
    }
    lock.lock();
    try {
      Product product = productDao.getProduct(productId);
      CartItem cartItem = findItem(cart, product);
      if (cartItem == null) {
        if (quantity > product.getStock()) {
          throw new OutOfStockException();
        }
        cart.getCartItemList().add(new CartItem(product, quantity));
      } else {
        if (quantity + cartItem.getQuantity() > product.getStock()) {
          throw new OutOfStockException();
        }
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
      }
    } finally {
      lock.unlock();
    }
  }

  private CartItem findItem(Cart cart, Product product) {
    return cart.getCartItemList().stream()
            .filter(cartItem -> product.getId().equals(cartItem.getProduct().getId()))
            .findAny().orElse(null);
  }
}
