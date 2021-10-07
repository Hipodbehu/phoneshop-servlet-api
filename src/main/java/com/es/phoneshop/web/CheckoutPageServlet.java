package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.service.CartService;
import com.es.phoneshop.model.cart.service.DefaultCartService;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.PaymentMethod;
import com.es.phoneshop.model.order.service.DefaultOrderService;
import com.es.phoneshop.model.order.service.OrderService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CheckoutPageServlet extends HttpServlet {
  public static final String CHECKOUT_PAGE = "/WEB-INF/pages/checkout.jsp";
  public static final String ERRORS_ATTRIBUTE = "errors";
  public static final String ORDER_ATTRIBUTE = "order";
  public static final String PAYMENT_METHODS_ATTRIBUTE = "paymentMethods";
  public static final String PAYMENT_METHOD_PARAMETER = "paymentMethod";
  public static final String NO_VALUE_MESSAGE = "Value is required";
  public static final String FIRST_NAME_PARAMETER = "firstName";
  public static final String LAST_NAME_PARAMETER = "lastName";
  public static final String PHONE_PARAMETER = "phone";
  public static final String DELIVERY_DATE_PARAMETER = "deliveryDate";
  public static final String DELIVERY_ADDRESS_PARAMETER = "deliveryAddress";
  public static final String INCORRECT_DATE_MESSAGE = "Incorrect date, must be like 2010-12-28";
  private CartService cartService;
  private OrderService orderService;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    this.cartService = DefaultCartService.getInstance();
    this.orderService = DefaultOrderService.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Cart cart = cartService.getCart(request);
    request.setAttribute(ORDER_ATTRIBUTE, orderService.getOrder(cart));
    request.setAttribute(PAYMENT_METHODS_ATTRIBUTE, orderService.getPaymentMethods());
    request.getRequestDispatcher(CHECKOUT_PAGE).forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Cart cart = cartService.getCart(request);
    Order order = orderService.getOrder(cart);
    Map<String, String> errors = new HashMap<>();

    setRequiredParameter(request, FIRST_NAME_PARAMETER, errors, order::setFirstName);
    setRequiredParameter(request, LAST_NAME_PARAMETER, errors, order::setLastName);
    setRequiredParameter(request, PHONE_PARAMETER, errors, order::setPhone);
    setDeliveryDate(request, errors, order);
    setRequiredParameter(request, DELIVERY_ADDRESS_PARAMETER, errors, order::setDeliveryAddress);
    setPaymentMethod(request, errors, order);
    if (errors.isEmpty()) {
      orderService.placeOrder(order);
      cartService.clearCart(cart);
      response.sendRedirect(request.getContextPath() + "/order/overview/" + order.getSecureId());
    } else {
      request.setAttribute(ERRORS_ATTRIBUTE, errors);
      request.setAttribute(ORDER_ATTRIBUTE, order);
      request.setAttribute(PAYMENT_METHODS_ATTRIBUTE, orderService.getPaymentMethods());
      request.getRequestDispatcher(CHECKOUT_PAGE).forward(request, response);
    }
  }

  private void setRequiredParameter(HttpServletRequest request, String parameter, Map<String, String> errors,
                                    Consumer<String> consumer) {
    String value = request.getParameter(parameter);
    if (value == null || value.isEmpty()) {
      errors.put(parameter, NO_VALUE_MESSAGE);
    } else {
      consumer.accept(value);
    }
  }

  private void setDeliveryDate(HttpServletRequest request, Map<String, String> errors, Order order) {
    String value = request.getParameter(DELIVERY_DATE_PARAMETER);
    if (value == null || value.isEmpty()) {
      errors.put(DELIVERY_DATE_PARAMETER, NO_VALUE_MESSAGE);
    } else {
      try {
        LocalDate deliveryDate = LocalDate.parse(value);
        if (deliveryDate.isBefore(LocalDate.now())) {
          errors.put(DELIVERY_DATE_PARAMETER, INCORRECT_DATE_MESSAGE);
        } else {
          order.setDeliveryDate(deliveryDate);
        }
      } catch (DateTimeParseException exception) {
        errors.put(DELIVERY_DATE_PARAMETER, INCORRECT_DATE_MESSAGE);
      }
    }
  }

  private void setPaymentMethod(HttpServletRequest request, Map<String, String> errors, Order order) {
    String value = request.getParameter(PAYMENT_METHOD_PARAMETER);
    if (value == null || value.isEmpty()) {
      errors.put(PAYMENT_METHOD_PARAMETER, NO_VALUE_MESSAGE);
    } else {
      order.setPaymentMethod(PaymentMethod.valueOf(value));
    }
  }
}
