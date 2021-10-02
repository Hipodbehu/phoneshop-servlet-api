package com.es.phoneshop.model.order.dao;

import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;

public interface OrderDao {
  Order getOrder(Long id) throws OrderNotFoundException;

  Order getOrderBySecureId(String id) throws OrderNotFoundException;

  void save(Order order);
}
