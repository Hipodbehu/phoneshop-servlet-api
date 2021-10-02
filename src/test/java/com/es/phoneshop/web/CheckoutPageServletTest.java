package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.service.CartService;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutPageServletTest {
  @Mock
  private HttpServletRequest request;
  @Mock
  private HttpServletResponse response;
  @Mock
  private RequestDispatcher requestDispatcher;
  @Mock
  private CartService cartService;
  @Mock
  private OrderService orderService;
  @InjectMocks
  private CheckoutPageServlet servlet = new CheckoutPageServlet();

  @Before
  public void setup() {
    when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    when(orderService.getOrder(any())).thenReturn(new Order());
    when(request.getParameter(anyString())).thenReturn("1");
    when(request.getParameter("deliveryDate")).thenReturn("2022-10-10");
    when(request.getParameter("paymentMethod")).thenReturn("CASH");
  }

  @Test
  public void testDoGet() throws ServletException, IOException {
    servlet.doGet(request, response);
    verify(requestDispatcher).forward(request, response);
  }

  @Test
  public void testDoPost() throws ServletException, IOException {
    servlet.doPost(request, response);
    verify(request).getContextPath();
  }

  @Test
  public void testDoPostWrongParameter() throws ServletException, IOException {
    when(request.getParameter("deliveryDate")).thenReturn("2020-10-10");
    servlet.doPost(request, response);
    verify(request).getRequestDispatcher(anyString());
  }
}