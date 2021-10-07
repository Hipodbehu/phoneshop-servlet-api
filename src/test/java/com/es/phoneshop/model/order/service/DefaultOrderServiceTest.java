package com.es.phoneshop.model.order.service;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.PaymentMethod;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class DefaultOrderServiceTest {
  private static OrderService orderService;

  @BeforeClass
  public static void setup() {
    orderService = DefaultOrderService.getInstance();
  }

  @Test
  public void testGetOrder() {
    Cart cart = new Cart();
    cart.setTotalCost(BigDecimal.TEN);
    cart.setTotalQuantity(10);
    Order order = orderService.getOrder(cart);
    assertEquals(order.getSubtotal(), cart.getTotalCost());
  }

  @Test
  public void testGetPaymentMethods() {
    List<PaymentMethod> paymentMethodList = Arrays.stream(PaymentMethod.values()).collect(Collectors.toList());
    paymentMethodList.removeAll(orderService.getPaymentMethods());
    assertTrue(paymentMethodList.isEmpty());
  }

  @Test
  public void testPlaceOrder() {
    Order order = new Order();
    orderService.placeOrder(order);
    assertNotNull(order.getSecureId());
  }
}
