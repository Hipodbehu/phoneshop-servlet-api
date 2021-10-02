package com.es.phoneshop.web.helper;

import javax.servlet.http.HttpServletRequest;
import java.text.NumberFormat;
import java.text.ParseException;

public class DefaultParseHelper implements ParseHelper {
  private static volatile DefaultParseHelper instance;

  private DefaultParseHelper() {
  }

  public static DefaultParseHelper getInstance() {
    DefaultParseHelper localInstance = instance;
    if (localInstance == null) {
      synchronized (DefaultParseHelper.class) {
        localInstance = instance;
        if (localInstance == null) {
          localInstance = new DefaultParseHelper();
          instance = localInstance;
        }
      }
    }
    return localInstance;
  }

  @Override
  public int parseQuantity(HttpServletRequest request, String quantityParam) throws ParseException {
    NumberFormat numberFormat = NumberFormat.getInstance(request.getLocale());
    return numberFormat.parse(quantityParam).intValue();
  }
}
