package com.es.phoneshop.exception;

public class BadQuantityException extends Exception {
  public BadQuantityException() {
    super();
  }

  public BadQuantityException(String message) {
    super(message);
  }

  public BadQuantityException(String message, Throwable cause) {
    super(message, cause);
  }

  public BadQuantityException(Throwable cause) {
    super(cause);
  }
}
