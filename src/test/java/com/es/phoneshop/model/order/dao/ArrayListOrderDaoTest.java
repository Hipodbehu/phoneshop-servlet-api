package com.es.phoneshop.model.order.dao;

import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.order.Order;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayListOrderDaoTest {
  private static OrderDao orderDao;

  @BeforeClass
  public static void setup() {
    orderDao = ArrayListOrderDao.getInstance();
    setTestData();
  }

  private static void setTestData() {
    Order order = new Order();
    order.setSecureId("testOrderId");
    orderDao.save(order);
  }

  @Test
  public void testGetOrder() throws ProductNotFoundException {
    assertNotNull(orderDao.getOrder(1L));
  }

  @Test(expected = OrderNotFoundException.class)
  public void testGetOrderNotFound() throws OrderNotFoundException {
    orderDao.getOrder(null);
  }

  @Test
  public void testSaveNewProduct() throws ProductNotFoundException {
    Order order = new Order();
    order.setSecureId("testId");
    orderDao.save(order);
    assertTrue(order.getId() >= 0);
    Order actual = orderDao.getOrder(order.getId());
    assertEquals("testId", actual.getSecureId());
  }

  @Test
  public void testGetOrderBySecureId() throws OrderNotFoundException {
    Order order = new Order();
    order.setSecureId("testId");
    orderDao.save(order);
    Order actual = orderDao.getOrderBySecureId(order.getSecureId());
    assertEquals("testId", actual.getSecureId());
  }

  @Test(expected = OrderNotFoundException.class)
  public void testGetOrderBySecureIdNotFound() throws OrderNotFoundException {
    Order order = new Order();
    order.setSecureId("testId");
    orderDao.save(order);
    Order actual = orderDao.getOrderBySecureId("wrongId");
    assertEquals("testId", actual.getSecureId());
  }
}
