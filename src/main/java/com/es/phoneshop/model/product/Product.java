package com.es.phoneshop.model.product;

import com.es.phoneshop.exception.ProductNotFullException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class Product implements Serializable {
  private Long id;
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
    this.id = id;
    this.code = code;
    this.description = description;
    this.price = price;
    this.currency = currency;
    this.stock = stock;
    this.imageUrl = imageUrl;
    priceHistories = new ArrayList<>();
    priceHistories.add(new PriceHistory(price));
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Product product = (Product) o;

    if (stock != product.stock) return false;
    if (id != null ? !id.equals(product.id) : product.id != null) return false;
    if (code != null ? !code.equals(product.code) : product.code != null) return false;
    if (description != null ? !description.equals(product.description) : product.description != null) return false;
    if (price != null ? !price.equals(product.price) : product.price != null) return false;
    if (currency != null ? !currency.equals(product.currency) : product.currency != null) return false;
    if (imageUrl != null ? !imageUrl.equals(product.imageUrl) : product.imageUrl != null) return false;
    return priceHistories != null ? priceHistories.equals(product.priceHistories) : product.priceHistories == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (code != null ? code.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (price != null ? price.hashCode() : 0);
    result = 31 * result + (currency != null ? currency.hashCode() : 0);
    result = 31 * result + stock;
    result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
    result = 31 * result + (priceHistories != null ? priceHistories.hashCode() : 0);
    return result;
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