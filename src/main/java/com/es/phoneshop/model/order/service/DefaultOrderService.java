package com.es.phoneshop.model.order.service;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.PaymentMethod;
import com.es.phoneshop.model.order.dao.ArrayListOrderDao;
import com.es.phoneshop.model.order.dao.OrderDao;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {
  private static volatile DefaultOrderService instance;
  private OrderDao orderDao;

  private DefaultOrderService() {
    this.orderDao = ArrayListOrderDao.getInstance();
  }

  public static DefaultOrderService getInstance() {
    DefaultOrderService localInstance = instance;
    if (localInstance == null) {
      synchronized (DefaultOrderService.class) {
        localInstance = instance;
        if (localInstance == null) {
          localInstance = new DefaultOrderService();
          instance = localInstance;
        }
      }
    }
    return localInstance;
  }

  @Override
  public Order getOrder(Cart cart) {
    Order order = new Order();
    order.setCartItemList(cart.getCartItemList().stream()
            .map(CartItem::new)
            .collect(Collectors.toList()));
    order.setSubtotal(cart.getTotalCost());
    order.setDeliveryCost(calculateDeliveryCost());
    order.setTotalCost(order.getSubtotal().add(order.getDeliveryCost()));
    order.setTotalQuantity(cart.getTotalQuantity());
    return order;
  }

  @Override
  public List<PaymentMethod> getPaymentMethods() {
    return Arrays.asList(PaymentMethod.values());
  }

  @Override
  public void placeOrder(Order order) {
    orderDao.save(order);
    order.setSecureId(UUID.randomUUID().toString());
  }

  private BigDecimal calculateDeliveryCost() {
    return BigDecimal.TEN;
  }
}
