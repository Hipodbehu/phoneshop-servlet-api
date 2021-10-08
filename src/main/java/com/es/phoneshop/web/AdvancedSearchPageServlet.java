package com.es.phoneshop.web;

import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.ProductDao;
import com.es.phoneshop.model.search.SearchType;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdvancedSearchPageServlet extends HttpServlet {
  public static final String ADVANCED_SEARCH_LIST_PAGE = "/WEB-INF/pages/advancedSearch.jsp";
  public static final String PRODUCTS_ATTRIBUTE = "products";
  public static final String SEARCH_TYPES_ATTRIBUTE = "searchTypes";
  public static final String ERRORS_ATTRIBUTE = "errors";
  public static final String DESCRIPTION_PARAMETER = "description";
  public static final String SEARCH_TYPE_PARAMETER = "searchType";
  public static final String MIN_PRICE_PARAMETER = "minPrice";
  public static final String MAX_PRICE_PARAMETER = "maxPrice";
  public static final String INCORRECT_NUMBER_FORMAT_MESSAGE = "Incorrect number format";
  private ProductDao productDao;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    this.productDao = ArrayListProductDao.getInstance();
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setAttribute(SEARCH_TYPES_ATTRIBUTE, getSearchTypes());
    request.getRequestDispatcher(ADVANCED_SEARCH_LIST_PAGE).forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String description = request.getParameter(DESCRIPTION_PARAMETER);
    Map<String, String> errors = new HashMap<>();
    BigDecimal minPrice = getPrice(request, MIN_PRICE_PARAMETER, errors);
    BigDecimal maxPrice = getPrice(request, MAX_PRICE_PARAMETER, errors);
    SearchType searchType = getSearchType(request);

    if (errors.isEmpty()) {
      request.setAttribute(PRODUCTS_ATTRIBUTE, productDao.advancedSearchProducts(description, minPrice, maxPrice, searchType));
    } else {
      request.setAttribute(ERRORS_ATTRIBUTE, errors);
    }
    doGet(request, response);
  }

  private BigDecimal getPrice(HttpServletRequest request, String paramName, Map<String, String> errors) {
    String priceParam = request.getParameter(paramName);
    BigDecimal price = null;
    if (priceParam != null && !priceParam.isEmpty()) {
      try {
        price = new BigDecimal(priceParam);
      } catch (NumberFormatException exception) {
        errors.put(paramName, INCORRECT_NUMBER_FORMAT_MESSAGE);
      }
    }
    return price;
  }

  private List<SearchType> getSearchTypes() {
    return Arrays.stream(SearchType.values()).collect(Collectors.toList());
  }

  private SearchType getSearchType(HttpServletRequest request) {
    String searchType = request.getParameter(SEARCH_TYPE_PARAMETER);
    return SearchType.valueOf(searchType);
  }
}
