package com.es.phoneshop.security;

import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultDosProtectionService implements DosProtectionService {
  public static final int MINUTES_TO_REFRESH = 1;
  private static volatile DefaultDosProtectionService instance;
  private static final long THRESHOLD = 20;
  private Map<String, SecurityItem> countMap;

  private DefaultDosProtectionService() {
    countMap = new ConcurrentHashMap<>();
  }

  public static DefaultDosProtectionService getInstance() {
    DefaultDosProtectionService localInstance = instance;
    if (localInstance == null) {
      synchronized (DefaultDosProtectionService.class) {
        localInstance = instance;
        if (localInstance == null) {
          localInstance = new DefaultDosProtectionService();
          instance = localInstance;
        }
      }
    }
    return localInstance;
  }

  @Override
  public boolean isAllowed(String ip) {
    SecurityItem securityItem = countMap.get(ip);
    boolean allowed = true;
    if (securityItem != null) {
      if (isAfterTime(securityItem)) {
        securityItem.setCount(1);
        securityItem.setLocalTime(LocalTime.now());
      } else {
        allowed = securityItem.getCount() < THRESHOLD;
        if (allowed) {
          securityItem.setCount(securityItem.getCount() + 1);
        }
      }
    } else {
      securityItem = new SecurityItem(LocalTime.now(), 1);
      countMap.put(ip, securityItem);
    }
    return allowed;
  }

  private boolean isAfterTime(SecurityItem securityItem) {
    return securityItem.getLocalTime().plusMinutes(MINUTES_TO_REFRESH).isBefore(LocalTime.now());
  }
}
