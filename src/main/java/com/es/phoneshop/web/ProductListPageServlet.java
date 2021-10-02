package com.es.phoneshop.web;

import com.es.phoneshop.exception.BadQuantityException;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.cart.service.CartService;
import com.es.phoneshop.model.cart.service.DefaultCartService;
import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.ProductDao;
import com.es.phoneshop.model.sort.SortField;
import com.es.phoneshop.model.sort.SortOrder;
import com.es.phoneshop.web.helper.DefaultParseHelper;
import com.es.phoneshop.web.helper.ParseHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;

public class ProductListPageServlet extends HttpServlet {
  public static final String PRODUCT_LIST_PAGE = "/WEB-INF/pages/productList.jsp";
  public static final String QUERY_PARAMETER = "query";
  public static final String SORT_PARAMETER = "sort";
  public static final String ORDER_PARAMETER = "order";
  public static final String QUANTITY_PARAMETER = "quantity";
  public static final String PRODUCT_ID_PARAMETER = "productId";
  public static final String INDEX_PARAMETER = "index";
  public static final String PRODUCTS_ATTRIBUTE = "products";
  public static final String ERROR_ATTRIBUTE = "error";
  public static final String SUCCESS_MESSAGE = "Cart updated successfully";
  public static final String OUT_OF_STOCK_MESSAGE = "Out of stock, max:";
  public static final String NOT_A_NUMBER_MESSAGE = "Not a number";
  public static final String BAD_QUANTITY_MESSAGE = "Bad quantity";
  private ProductDao productDao;
  private CartService cartService;
  private ParseHelper parseHelper;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    this.productDao = ArrayListProductDao.getInstance();
    this.cartService = DefaultCartService.getInstance();
    this.parseHelper = DefaultParseHelper.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String query = request.getParameter(QUERY_PARAMETER);
    request.setAttribute(PRODUCTS_ATTRIBUTE, productDao.findProducts(query,
            getSortField(request), getSortOrder(request)));
    request.getRequestDispatcher(PRODUCT_LIST_PAGE).forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String indexParam = request.getParameter(INDEX_PARAMETER);
    String idParam = request.getParameterValues(PRODUCT_ID_PARAMETER)[Integer.parseInt(indexParam)];
    try {
      long id = Long.parseLong(idParam);
      String quantityParam = request.getParameterValues(QUANTITY_PARAMETER)[Integer.parseInt(indexParam)];
      int quantity = parseHelper.parseQuantity(request, quantityParam);
      cartService.add(cartService.getCart(request), id, quantity);
    } catch (NumberFormatException exception) {
      throw new ProductNotFoundException(idParam, exception);
    } catch (OutOfStockException exception) {
      request.setAttribute(ERROR_ATTRIBUTE, OUT_OF_STOCK_MESSAGE + exception.getStockAvailable());
      request.setAttribute(PRODUCT_ID_PARAMETER, idParam);
      doGet(request, response);
      return;
    } catch (ParseException exception) {
      request.setAttribute(ERROR_ATTRIBUTE, NOT_A_NUMBER_MESSAGE);
      request.setAttribute(PRODUCT_ID_PARAMETER, idParam);
      doGet(request, response);
      return;
    } catch (BadQuantityException exception) {
      request.setAttribute(ERROR_ATTRIBUTE, BAD_QUANTITY_MESSAGE);
      request.setAttribute(PRODUCT_ID_PARAMETER, idParam);
      doGet(request, response);
      return;
    }
    response.sendRedirect(request.getContextPath() + "/products" + "?message=" + SUCCESS_MESSAGE);
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
