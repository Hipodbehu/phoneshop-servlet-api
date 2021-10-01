package com.es.phoneshop.web;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.service.CartService;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
  @Mock
  private HttpServletRequest request;
  @Mock
  private HttpServletResponse response;
  @Mock
  private HttpSession session;
  @Mock
  private RequestDispatcher requestDispatcher;
  @Mock
  private ArrayListProductDao productDao;
  @Mock
  private CartService cartService;
  @Mock
  private Product product;
  @InjectMocks
  private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

  @Before
  public void setup() {
    when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    when(request.getRequestURI()).thenReturn("/product");
    when(request.getPathInfo()).thenReturn("/1");
    when(request.getLocale()).thenReturn(new Locale("us"));
    when(request.getSession()).thenReturn(session);
    when(session.getAttribute("recentlyViewed")).thenReturn(new ArrayDeque<>());
    when(productDao.getProduct(anyLong())).thenReturn(product);
  }

  @Test
  public void testDoGet() throws ServletException, IOException {
    servlet.doGet(request, response);
    verify(requestDispatcher).forward(request, response);
  }

  @Test(expected = ProductNotFoundException.class)
  public void testDoGetWithIllegalProductId() throws ServletException, IOException {
    when(request.getPathInfo()).thenReturn("/illegalId");
    servlet.doGet(request, response);
  }

  @Test
  public void testDoPost() throws ServletException, IOException {
    when(request.getParameter(anyString())).thenReturn("1");
    servlet.doPost(request, response);
    verify(request).getPathInfo();
  }

  @Test
  public void testSetAttributes() throws ServletException, IOException {
    servlet.doGet(request, response);
    verify(request).setAttribute(eq("product"), any());
  }

  @Test
  public void testDaoInvokeMethod() throws ServletException, IOException {
    servlet.doGet(request, response);
    verify(productDao).getProduct(anyLong());
  }
}