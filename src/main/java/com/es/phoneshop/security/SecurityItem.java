package com.es.phoneshop.security;

import java.time.LocalTime;

public class SecurityItem {
  private LocalTime localTime;
  private int count;

  public SecurityItem(LocalTime localTime, int count) {
    this.localTime = localTime;
    this.count = count;
  }

  public LocalTime getLocalTime() {
    return localTime;
  }

  public void setLocalTime(LocalTime localTime) {
    this.localTime = localTime;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}
