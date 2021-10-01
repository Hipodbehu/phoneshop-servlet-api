package com.es.phoneshop.model.service;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.BadQuantityException;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantLock;

public class DefaultCartService implements CartService {
  public static final String CART_SESSION_ATTRIBUTE = DefaultCartService.class.getName() + ".cart";
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
  public Cart getCart(HttpServletRequest request) {
    lock.lock();
    Cart cart;
    try {
      cart = (Cart) request.getSession().getAttribute(CART_SESSION_ATTRIBUTE);
      if (cart == null) {
        cart = new Cart();
      }
      request.getSession().setAttribute(CART_SESSION_ATTRIBUTE, cart);
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
          throw new OutOfStockException(product.getStock());
        }
        cart.getCartItemList().add(new CartItem(product, quantity));
      } else {
        if (quantity + cartItem.getQuantity() > product.getStock()) {
          throw new OutOfStockException(product.getStock());
        }
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
      }
      recalculateCart(cart);
    } finally {
      lock.unlock();
    }
  }

  @Override
  public void update(Cart cart, Long productId, int quantity) throws OutOfStockException, BadQuantityException {
    if (quantity <= 0) {
      throw new BadQuantityException();
    }
    lock.lock();
    try {
      Product product = productDao.getProduct(productId);
      CartItem cartItem = findItem(cart, product);
      if (quantity > product.getStock()) {
        throw new OutOfStockException(product.getStock());
      }
      cartItem.setQuantity(quantity);
      recalculateCart(cart);
    } finally {
      lock.unlock();
    }
  }

  @Override
  public void delete(Cart cart, Long productId) {
    lock.lock();
    try {
      Product product = productDao.getProduct(productId);
      CartItem cartItem = findItem(cart, product);
      cart.getCartItemList().remove(cartItem);
      recalculateCart(cart);
    } finally {
      lock.unlock();
    }
  }

  private CartItem findItem(Cart cart, Product product) {
    return cart.getCartItemList().stream()
            .filter(cartItem -> product.getId().equals(cartItem.getProduct().getId()))
            .findAny().orElse(null);
  }

  private void recalculateCart(Cart cart) {
    cart.setTotalQuantity(cart.getCartItemList().stream()
            .map(CartItem::getQuantity)
            .reduce(Integer::sum)
            .orElse(0));
    cart.setTotalCost(cart.getCartItemList().stream()
            .map(cartItem -> cartItem.getProduct().getPrice()
                    .multiply(new BigDecimal(cartItem.getQuantity())))
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO));
  }
}
