package com.es.phoneshop.model.product.dao;

import com.es.phoneshop.dao.AbstractDao;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.sort.SortField;
import com.es.phoneshop.model.sort.SortOrder;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ArrayListProductDao extends AbstractDao<Product> implements ProductDao {
  private static volatile ArrayListProductDao instance;

  private ArrayListProductDao() {
  }

  public static ArrayListProductDao getInstance() {
    ArrayListProductDao localInstance = instance;
    if (localInstance == null) {
      synchronized (ArrayListProductDao.class) {
        localInstance = instance;
        if (localInstance == null) {
          localInstance = new ArrayListProductDao();
          instance = localInstance;
        }
      }
    }
    return localInstance;
  }

  @Override
  public Product getProduct(Long id) throws ProductNotFoundException {
    return getItem(id).orElseThrow(ProductNotFoundException::new);
  }

  @Override
  public List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
    List<Product> products = getItemList();
    List<Product> productList;
    String[] queryWords = getQueryWords(query);
    productList = products.stream()
            .filter(p -> p.getPrice() != null)
            .filter(p -> p.getStock() > 0)
            .filter(product -> shouldDisplayProduct(queryWords, product))
            .sorted(getSortOrderComparator(getSortFieldComparator(queryWords, sortField), sortOrder))
            .collect(Collectors.toList());
    return productList;
  }

  private String[] getQueryWords(String query) {
    return (query == null || query.trim().isEmpty())
            ? null
            : query.trim().toLowerCase(Locale.ROOT).split("\\s");
  }

  private boolean shouldDisplayProduct(String[] queryWords, Product product) {
    return queryWords == null
            || Arrays.stream(queryWords).anyMatch(
            s -> product.getDescription().toLowerCase(Locale.ROOT).contains(s));
  }

  private Comparator<Product> getSortFieldComparator(String[] query, SortField sortField) {
    Comparator<Product> comparator;
    if (sortField == null) {
      comparator = Comparator.comparingLong(o -> getCountOfMatches(query, o));
    } else if (SortField.DESCRIPTION == sortField) {
      comparator = Comparator.comparing(Product::getDescription);
    } else {
      comparator = Comparator.comparing(Product::getPrice);
    }
    return comparator;
  }

  private Comparator<Product> getSortOrderComparator(Comparator<Product> comparator, SortOrder sortOrder) {
    if (sortOrder == null || SortOrder.DESC == sortOrder) {
      comparator = comparator.reversed();
    }
    return comparator;
  }

  private long getCountOfMatches(String[] query, Product product) {
    long count = 0;
    if (query != null) {
      count = Arrays.stream(query)
              .filter(s -> product.getDescription().toLowerCase(Locale.ROOT).contains(s))
              .count();
    }
    return count;
  }
}
