package com.es.phoneshop.web;

import com.es.phoneshop.exception.BadQuantityException;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.exception.ProductNotFoundException;
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
import java.util.HashMap;
import java.util.Map;

public class CartPageServlet extends HttpServlet {
  public static final String CART_PAGE = "/WEB-INF/pages/cart.jsp";
  public static final String SUCCESS_MESSAGE = "Cart updated successfully";
  public static final String OUT_OF_STOCK_MESSAGE = "Out of stock, max:";
  public static final String NOT_A_NUMBER_MESSAGE = "Not a number";
  public static final String BAD_QUANTITY_MESSAGE = "Bad quantity";
  public static final String PRODUCT_ID_PARAMETER = "productId";
  public static final String QUANTITY_PARAMETER = "quantity";
  public static final String ERRORS_ATTRIBUTE = "errors";
  private CartService cartService;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    cartService = DefaultCartService.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.getRequestDispatcher(CART_PAGE).forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String[] productIds = request.getParameterValues(PRODUCT_ID_PARAMETER);
    String[] quantities = request.getParameterValues(QUANTITY_PARAMETER);
    Map<Long, String> errors = new HashMap<>();
    String message = SUCCESS_MESSAGE;
    if (productIds != null) {
      for (int i = 0; i < productIds.length; i++) {
        try {
          long id = Long.parseLong(productIds[i]);
          int quantity = parseQuantity(request, quantities[i]);
          cartService.update(cartService.getCart(request), id, quantity);
        } catch (NumberFormatException exception) {
          throw new ProductNotFoundException(productIds[i], exception);
        } catch (OutOfStockException exception) {
          errors.put(Long.parseLong(productIds[i]), OUT_OF_STOCK_MESSAGE + exception.getStockAvailable());
        } catch (ParseException exception) {
          errors.put(Long.parseLong(productIds[i]), NOT_A_NUMBER_MESSAGE);
        } catch (BadQuantityException e) {
          errors.put(Long.parseLong(productIds[i]), BAD_QUANTITY_MESSAGE);
        }
      }
    } else {
      message = "";
    }
    if (errors.isEmpty()) {
      response.sendRedirect(request.getContextPath() + "/cart" + "?message=" + message);
    } else {
      request.setAttribute(ERRORS_ATTRIBUTE, errors);
      doGet(request, response);
    }
  }

  private int parseQuantity(HttpServletRequest request, String quantityParam) throws ParseException {
    NumberFormat numberFormat = NumberFormat.getInstance(request.getLocale());
    return numberFormat.parse(quantityParam).intValue();
  }
}
