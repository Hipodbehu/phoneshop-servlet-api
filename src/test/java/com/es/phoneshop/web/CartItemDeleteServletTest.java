package com.es.phoneshop.web;

import com.es.phoneshop.model.service.CartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartItemDeleteServletTest {
  @Mock
  private HttpServletRequest request;
  @Mock
  private HttpServletResponse response;
  @Mock
  private CartService cartService;
  @InjectMocks
  private CartItemDeleteServlet servlet = new CartItemDeleteServlet();

  @Test
  public void testDoPost() throws ServletException, IOException {
    when(request.getPathInfo()).thenReturn("/1");
    servlet.doPost(request, response);
    verify(request).getContextPath();
  }
}