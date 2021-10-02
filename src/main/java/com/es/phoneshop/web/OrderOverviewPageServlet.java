package com.es.phoneshop.web;

import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.dao.ArrayListOrderDao;
import com.es.phoneshop.model.order.dao.OrderDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {
  public static final String ORDER_OVERVIEW_PAGE = "/WEB-INF/pages/overview.jsp";
  public static final String ORDER_ATTRIBUTE = "order";
  private OrderDao orderDao;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    this.orderDao = ArrayListOrderDao.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String idParam = request.getPathInfo().substring(1);
    try {
      Order order = orderDao.getOrderBySecureId(idParam);
      request.setAttribute(ORDER_ATTRIBUTE, order);
    } catch (NumberFormatException exception) {
      throw new OrderNotFoundException(idParam, exception);
    }
    request.getRequestDispatcher(ORDER_OVERVIEW_PAGE).forward(request, response);
  }
}
