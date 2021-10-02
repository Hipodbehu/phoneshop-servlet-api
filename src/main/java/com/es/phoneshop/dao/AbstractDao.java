package com.es.phoneshop.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class AbstractDao<T extends AbstractEntity> {
  private ReadWriteLock lock;
  private List<T> itemList;
  private long maxId;

  protected AbstractDao() {
    this.maxId = 1;
    this.itemList = new ArrayList<>();
    this.lock = new ReentrantReadWriteLock();
  }

  public Optional<T> getItem(Long id) {
    Optional<T> result;
    lock.readLock().lock();
    try {
      result = itemList.stream()
              .filter(item -> item.getId().equals(id))
              .findAny();
    } finally {
      lock.readLock().unlock();
    }
    return result;
  }

  public List<T> getItemList() {
    return itemList;
  }

  public void save(T item) {
    lock.writeLock().lock();
    try {
      if (item.getId() == null) {
        item.setId(maxId++);
        itemList.add(item);
      } else {
        update(item);
      }
    } finally {
      lock.writeLock().unlock();
    }
  }

  private void update(T item) {
    int index = itemList.indexOf(item);
    if (index < 0) {
      throw new IllegalArgumentException();
    }
    itemList.set(index, item);
  }

  public void delete(Long id) {
    lock.writeLock().lock();
    try {
      itemList.removeIf(item -> item.getId().equals(id));
    } finally {
      lock.writeLock().unlock();
    }
  }
}
