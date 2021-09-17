package com.es.phoneshop.model.service;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.BadQuantityException;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DefaultCartServiceTest {
  private static CartService cartService;
  private static ProductDao productDao;
  private static Currency usd;
  private static Product.ProductBuilder productBuilder;

  @BeforeClass
  public static void setup() {
    cartService = DefaultCartService.getInstance();
    productDao = ArrayListProductDao.getInstance();
    usd = Currency.getInstance("USD");
    productBuilder = new Product.ProductBuilder();
    setTestData();
  }

  private static void setTestData() {
    productDao.save(productBuilder.setCode("sgs2").setDescription("Samsung Galaxy S II").setPrice(new BigDecimal(200)).setCurrency(usd).setStock(20).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg").createProduct());
    productDao.save(productBuilder.setCode("sgs3").setDescription("Samsung Galaxy S III").setPrice(new BigDecimal(300)).setCurrency(usd).setStock(5).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg").createProduct());
    productDao.save(productBuilder.setCode("iphone").setDescription("Apple iPhone").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(10).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg").createProduct());
  }

  @Test
  public void testGetCart() {
    assertNotNull(cartService.getCart());
  }

  @Test
  public void testAdd() throws OutOfStockException, BadQuantityException {
    Cart cart = cartService.getCart();
    cartService.add(cart, 1L, 3);
    Product testProduct = productDao.getProduct(1L);
    CartItem cartItem = new CartItem(testProduct, 3);
    cart.getCartItemList().remove(cartItem);
    assertTrue(cart.getCartItemList().isEmpty());
  }

  @Test
  public void testAddSum() throws OutOfStockException, BadQuantityException {
    Cart cart = cartService.getCart();
    cartService.add(cart, 1L, 3);
    cartService.add(cart, 1L, 3);
    Product testProduct = productDao.getProduct(1L);
    CartItem cartItem = new CartItem(testProduct, 6);
    cart.getCartItemList().remove(cartItem);
    assertTrue(cart.getCartItemList().isEmpty());
  }

  @Test(expected = OutOfStockException.class)
  public void testAddOutOfStock() throws OutOfStockException, BadQuantityException {
    Cart cart = cartService.getCart();
    cartService.add(cart, 1L, Integer.MAX_VALUE);
  }

  @Test(expected = ProductNotFoundException.class)
  public void testAddProductNotFound() throws OutOfStockException, BadQuantityException {
    Cart cart = cartService.getCart();
    cartService.add(cart, 0L, 3);
  }
}
