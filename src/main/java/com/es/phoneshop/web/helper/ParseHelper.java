package com.es.phoneshop.web.helper;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

public interface ParseHelper {
  int parseQuantity(HttpServletRequest request, String quantityParam) throws ParseException;
}
