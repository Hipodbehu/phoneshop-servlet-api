package com.es.phoneshop.model.product;

import com.es.phoneshop.dao.AbstractEntity;
import com.es.phoneshop.exception.ProductNotFullException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class Product extends AbstractEntity implements Serializable {
  private String code;
  private String description;
  /**
   * null means there is no price because the product is outdated or new
   */
  private BigDecimal price;
  /**
   * can be null if the price is null
   */
  private Currency currency;
  private int stock;
  private String imageUrl;
  private List<PriceHistory> priceHistories;

  private Product(Long id, String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
    setId(id);
    this.code = code;
    this.description = description;
    this.price = price;
    this.currency = currency;
    this.stock = stock;
    this.imageUrl = imageUrl;
    priceHistories = new ArrayList<>();
    priceHistories.add(new PriceHistory(price));
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
    priceHistories.add(new PriceHistory(price));
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public List<PriceHistory> getPriceHistories() {
    return new ArrayList<>(priceHistories);
  }

  public static class ProductBuilder {
    private String code;
    private String description;
    private BigDecimal price;
    private Currency currency;
    private int stock;
    private String imageUrl;
    private Long id;

    public ProductBuilder setCode(String code) {
      this.code = code;
      return this;
    }

    public ProductBuilder setDescription(String description) {
      this.description = description;
      return this;
    }

    public ProductBuilder setPrice(BigDecimal price) {
      this.price = price;
      return this;
    }

    public ProductBuilder setCurrency(Currency currency) {
      this.currency = currency;
      return this;
    }

    public ProductBuilder setStock(int stock) {
      this.stock = stock;
      return this;
    }

    public ProductBuilder setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
      return this;
    }

    public ProductBuilder setId(Long id) {
      this.id = id;
      return this;
    }

    public Product createProduct() {
      if (!isAllRequiredParamsSet()) {
        throw new ProductNotFullException();
      }
      Product product = new Product(id, code, description, price, currency, stock, imageUrl);
      id = null;
      return product;
    }

    private boolean isAllRequiredParamsSet() {
      return code != null || description != null || currency != null || imageUrl != null;
    }
  }
}