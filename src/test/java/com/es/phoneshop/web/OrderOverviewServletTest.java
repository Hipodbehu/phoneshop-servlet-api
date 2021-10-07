package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.service.CartService;
import com.es.phoneshop.model.order.dao.OrderDao;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderOverviewServletTest {
  @Mock
  private HttpServletRequest request;
  @Mock
  private HttpServletResponse response;
  @Mock
  private RequestDispatcher requestDispatcher;
  @Mock
  private CartService cartService;
  @Mock
  private OrderDao orderDao;
  @InjectMocks
  private OrderOverviewPageServlet servlet = new OrderOverviewPageServlet();

  @Test
  public void testDoGet() throws ServletException, IOException {
    when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    when(request.getPathInfo()).thenReturn("/1");
    servlet.doGet(request, response);
    verify(request).getRequestDispatcher(anyString());
  }
}