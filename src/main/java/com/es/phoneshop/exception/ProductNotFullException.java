package com.es.phoneshop.exception;

public class ProductNotFullException extends RuntimeException {
  public ProductNotFullException() {
  }

  public ProductNotFullException(String message) {
    super(message);
  }

  public ProductNotFullException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProductNotFullException(Throwable cause) {
    super(cause);
  }
}
