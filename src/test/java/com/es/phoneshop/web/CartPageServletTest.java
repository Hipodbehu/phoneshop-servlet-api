package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.service.CartService;
import com.es.phoneshop.web.helper.ParseHelper;
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
import java.util.Locale;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {
  @Mock
  private HttpServletRequest request;
  @Mock
  private HttpServletResponse response;
  @Mock
  private RequestDispatcher requestDispatcher;
  @Mock
  private CartService cartService;
  @Mock
  private ParseHelper parseHelper;
  @InjectMocks
  private CartPageServlet servlet = new CartPageServlet();

  @Before
  public void setup() throws ServletException {
    when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
  }

  @Test
  public void testDoGet() throws ServletException, IOException {
    servlet.doGet(request, response);
    verify(requestDispatcher).forward(request, response);
  }

  @Test
  public void testDoPost() throws ServletException, IOException {
    when(request.getParameterValues(anyString())).thenReturn(new String[]{"1"});
    servlet.doPost(request, response);
    verify(request).getContextPath();
  }
}