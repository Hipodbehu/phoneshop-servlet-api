package com.es.phoneshop.web;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.ProductNotFoundException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
  public static final String PRODUCT_HISTORY_PAGE = "/WEB-INF/pages/productHistory.jsp";
  public static final String PRODUCT_DETAILS_PAGE = "/WEB-INF/pages/productDetails.jsp";
  private ProductDao productDao;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    productDao = ArrayListProductDao.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String idParam = request.getPathInfo().substring(1);
    try {
      long id = Long.parseLong(idParam);
      request.setAttribute("product", productDao.getProduct(id));
    } catch (NumberFormatException exception) {
      throw new ProductNotFoundException(idParam, exception);
    }
    String page = PRODUCT_DETAILS_PAGE;
    if (request.getRequestURI().contains("history")) {
      page = PRODUCT_HISTORY_PAGE;
    }
    request.getRequestDispatcher(page).forward(request, response);
  }
}
