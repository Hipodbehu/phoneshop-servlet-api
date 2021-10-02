package com.es.phoneshop.model.order.dao;

import com.es.phoneshop.dao.AbstractDao;
import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;

public class ArrayListOrderDao extends AbstractDao<Order> implements OrderDao {
  private static volatile ArrayListOrderDao instance;

  private ArrayListOrderDao() {
  }

  public static ArrayListOrderDao getInstance() {
    ArrayListOrderDao localInstance = instance;
    if (localInstance == null) {
      synchronized (ArrayListOrderDao.class) {
        localInstance = instance;
        if (localInstance == null) {
          localInstance = new ArrayListOrderDao();
          instance = localInstance;
        }
      }
    }
    return localInstance;
  }

  @Override
  public Order getOrder(Long id) throws OrderNotFoundException {
    return getItem(id).orElseThrow(OrderNotFoundException::new);
  }

  @Override
  public Order getOrderBySecureId(String id) throws OrderNotFoundException {
    return getItemList().stream()
            .filter(order -> order.getSecureId().equals(id))
            .findAny().orElseThrow(OrderNotFoundException::new);
  }
}
