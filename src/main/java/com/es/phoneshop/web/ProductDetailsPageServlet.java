package com.es.phoneshop.web;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.BadQuantityException;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.service.CartService;
import com.es.phoneshop.model.service.DefaultCartService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayDeque;
import java.util.Queue;

public class ProductDetailsPageServlet extends HttpServlet {
  public static final String RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE = "recentlyViewed";
  public static final String PRODUCT_HISTORY_PAGE = "/WEB-INF/pages/productHistory.jsp";
  public static final String PRODUCT_DETAILS_PAGE = "/WEB-INF/pages/productDetails.jsp";
  public static final String OUT_OF_STOCK_MESSAGE = "Out of stock, max:";
  public static final String NOT_A_NUMBER_MESSAGE = "Not a number";
  public static final String SUCCESS_MESSAGE = "Added to cart successfully";
  public static final String PRODUCT_ATTRIBUTE = "product";
  public static final String ERROR_ATTRIBUTE = "error";
  public static final String QUANTITY_PARAMETER = "quantity";
  public static final String BAD_QUANTITY_MESSAGE = "Bad quantity";
  public static final int MAX_RECENTLY_VIEWED_COUNT = 3;
  private ProductDao productDao;
  private CartService cartService;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    productDao = ArrayListProductDao.getInstance();
    cartService = DefaultCartService.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String idParam = request.getPathInfo().substring(1);
    try {
      long id = Long.parseLong(idParam);
      Product product = productDao.getProduct(id);
      addToRecentlyViewed(getRecentlyViewed(request), product);
      request.setAttribute(PRODUCT_ATTRIBUTE, product);
    } catch (NumberFormatException exception) {
      throw new ProductNotFoundException(idParam, exception);
    }
    String page = PRODUCT_DETAILS_PAGE;
    if (request.getRequestURI().contains("history")) {
      page = PRODUCT_HISTORY_PAGE;
    }
    request.getRequestDispatcher(page).forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String idParam = request.getPathInfo().substring(1);
    try {
      long id = Long.parseLong(idParam);
      String quantityParam = request.getParameter(QUANTITY_PARAMETER);
      int quantity = parseQuantity(request, quantityParam);
      cartService.add(cartService.getCart(request), id, quantity);
    } catch (NumberFormatException exception) {
      throw new ProductNotFoundException(idParam, exception);
    } catch (OutOfStockException exception) {
      request.setAttribute(ERROR_ATTRIBUTE, OUT_OF_STOCK_MESSAGE + exception.getStockAvailable());
      doGet(request, response);
      return;
    } catch (ParseException exception) {
      request.setAttribute(ERROR_ATTRIBUTE, NOT_A_NUMBER_MESSAGE);
      doGet(request, response);
      return;
    } catch (BadQuantityException e) {
      request.setAttribute(ERROR_ATTRIBUTE, BAD_QUANTITY_MESSAGE);
      doGet(request, response);
      return;
    }
    response.sendRedirect(request.getContextPath() + "/cart" + "?message=" + SUCCESS_MESSAGE);
  }

  private void addToRecentlyViewed(Queue<Product> products, Product product) {
    products.remove(product);
    if ((products.size() >= MAX_RECENTLY_VIEWED_COUNT)) {
      products.poll();
    }
    products.offer(product);
  }

  private Queue<Product> getRecentlyViewed(HttpServletRequest request) {
    Queue<Product> products = (Queue<Product>) request.getSession().getAttribute(RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE);
    if (products == null) {
      products = new ArrayDeque<>();
    }
    request.getSession().setAttribute(RECENTLY_VIEWED_PRODUCTS_SESSION_ATTRIBUTE, products);
    return products;
  }

  private int parseQuantity(HttpServletRequest request, String quantityParam) throws ParseException {
    NumberFormat numberFormat = NumberFormat.getInstance(request.getLocale());
    return numberFormat.parse(quantityParam).intValue();
  }
}
