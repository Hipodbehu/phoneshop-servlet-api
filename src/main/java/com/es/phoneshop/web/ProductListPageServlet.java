package com.es.phoneshop.web;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.sort.SortField;
import com.es.phoneshop.model.sort.SortOrder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

public class ProductListPageServlet extends HttpServlet {
  public static final String QUERY_PARAMETER = "query";
  public static final String PRODUCTS_ATTRIBUTE = "products";
  public static final String PRODUCT_LIST_PAGE = "/WEB-INF/pages/productList.jsp";
  public static final String SORT_PARAMETER = "sort";
  public static final String ORDER_PARAMETER = "order";
  private ProductDao productDao;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    productDao = ArrayListProductDao.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String query = request.getParameter(QUERY_PARAMETER);
    request.setAttribute(PRODUCTS_ATTRIBUTE, productDao.findProducts(query,
            getSortField(request), getSortOrder(request)));
    request.getRequestDispatcher(PRODUCT_LIST_PAGE).forward(request, response);
  }

  private SortField getSortField(HttpServletRequest request) {
    String sortField = request.getParameter(SORT_PARAMETER);
    return sortField != null ? SortField.valueOf(sortField.toUpperCase(Locale.ROOT)) : null;
  }

  private SortOrder getSortOrder(HttpServletRequest request) {
    String sortOrder = request.getParameter(ORDER_PARAMETER);
    return sortOrder != null ? SortOrder.valueOf(sortOrder.toUpperCase(Locale.ROOT)) : null;
  }
}
