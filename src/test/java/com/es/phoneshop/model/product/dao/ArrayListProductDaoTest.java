package com.es.phoneshop.model.product.dao;

import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.sort.SortField;
import com.es.phoneshop.model.sort.SortOrder;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
  private static ProductDao productDao;
  private static List<Product> productList;
  private static Currency usd;
  private static Product.ProductBuilder productBuilder;

  @BeforeClass
  public static void setup() {
    productDao = ArrayListProductDao.getInstance();
    productList = new ArrayList<>();
    usd = Currency.getInstance("USD");
    productBuilder = new Product.ProductBuilder();
    setTestData();
  }

  private static void setTestData() {
    Product product = productBuilder.setCode("sgs2").setDescription("Samsung Galaxy S II").setPrice(new BigDecimal(200)).setCurrency(usd).setStock(2).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg").createProduct();
    productList.add(product);
    productDao.save(product);
    product = productBuilder.setCode("sgs3").setDescription("Samsung Galaxy S III").setPrice(new BigDecimal(300)).setCurrency(usd).setStock(5).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg").createProduct();
    productList.add(product);
    productDao.save(product);
    product = productBuilder.setCode("iphone").setDescription("Apple iPhone").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(10).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg").createProduct();
    productList.add(product);
    productDao.save(product);
  }

  @After
  public void clear() {
    List<Product> products = productDao.findProducts(null, null, null);
    products.removeAll(productList);
    products.forEach(product -> productDao.delete(product.getId()));
  }

  @Test
  public void testFindProducts() {
    List<Product> testList = new ArrayList<>();
    testList.add(productBuilder.setId(1L).setCode("sgs2").setDescription("Samsung Galaxy S II").setPrice(new BigDecimal(200)).setCurrency(usd).setStock(2).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg").createProduct());
    testList.add(productBuilder.setId(2L).setCode("sgs3").setDescription("Samsung Galaxy S III").setPrice(new BigDecimal(300)).setCurrency(usd).setStock(5).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg").createProduct());
    testList.removeAll(productDao.findProducts("Samsung", null, null));
    assertTrue(testList.isEmpty());
  }

  @Test
  public void testFindProductsNullQuery() {
    assertFalse(productDao.findProducts(null, null, null).isEmpty());
  }

  @Test
  public void testFindProductsWithZeroStock() {
    Product product = productBuilder.setCode("test-code").setDescription("test-description").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(0).setImageUrl("test-url").createProduct();
    productDao.save(product);
    assertFalse(productDao.findProducts(null, null, null).contains(product));
    productDao.delete(product.getId());
  }

  @Test
  public void testFindProductsWithNullPrice() {
    Product product = productBuilder.setCode("test-code").setDescription("test-description").setPrice(null).setCurrency(usd).setStock(100).setImageUrl("test-url").createProduct();
    productDao.save(product);
    assertFalse(productDao.findProducts(null, null, null).contains(product));
    productDao.delete(product.getId());
  }

  @Test
  public void testSortProductsDescending() {
    List<Product> testList = new ArrayList<>();
    testList.add(productBuilder.setId(3L).setCode("iphone").setDescription("Apple iPhone").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(10).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg").createProduct());
    testList.add(productBuilder.setId(1L).setCode("sgs2").setDescription("Samsung Galaxy S II").setPrice(new BigDecimal(200)).setCurrency(usd).setStock(2).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg").createProduct());
    testList.add(productBuilder.setId(2L).setCode("sgs3").setDescription("Samsung Galaxy S III").setPrice(new BigDecimal(300)).setCurrency(usd).setStock(5).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg").createProduct());
    assertEquals(testList, productDao.findProducts("h A", null, null));
  }

  @Test
  public void testSortProductsAscending() {
    List<Product> testList = new ArrayList<>();
    testList.add(productBuilder.setId(1L).setCode("sgs2").setDescription("Samsung Galaxy S II").setPrice(new BigDecimal(200)).setCurrency(usd).setStock(2).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg").createProduct());
    testList.add(productBuilder.setId(2L).setCode("sgs3").setDescription("Samsung Galaxy S III").setPrice(new BigDecimal(300)).setCurrency(usd).setStock(5).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg").createProduct());
    testList.add(productBuilder.setId(3L).setCode("iphone").setDescription("Apple iPhone").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(10).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg").createProduct());
    assertEquals(testList, productDao.findProducts("h A", null, SortOrder.ASC));
  }

  @Test
  public void testSortProductsByDescriptionDescending() {
    List<Product> testList = new ArrayList<>();
    testList.add(productBuilder.setId(2L).setCode("sgs3").setDescription("Samsung Galaxy S III").setPrice(new BigDecimal(300)).setCurrency(usd).setStock(5).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg").createProduct());
    testList.add(productBuilder.setId(1L).setCode("sgs2").setDescription("Samsung Galaxy S II").setPrice(new BigDecimal(200)).setCurrency(usd).setStock(2).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg").createProduct());
    testList.add(productBuilder.setId(3L).setCode("iphone").setDescription("Apple iPhone").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(10).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg").createProduct());
    assertEquals(testList, productDao.findProducts("", SortField.DESCRIPTION, null));
  }

  @Test
  public void testSortProductsByPriceDescending() {
    List<Product> testList = new ArrayList<>();
    testList.add(productBuilder.setId(2L).setCode("sgs3").setDescription("Samsung Galaxy S III").setPrice(new BigDecimal(300)).setCurrency(usd).setStock(5).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg").createProduct());
    testList.add(productBuilder.setId(1L).setCode("sgs2").setDescription("Samsung Galaxy S II").setPrice(new BigDecimal(200)).setCurrency(usd).setStock(2).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg").createProduct());
    testList.add(productBuilder.setId(3L).setCode("iphone").setDescription("Apple iPhone").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(10).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg").createProduct());
    assertEquals(testList, productDao.findProducts("", SortField.DESCRIPTION, null));
  }

  @Test
  public void testGetProduct() throws ProductNotFoundException {
    assertNotNull(productDao.getProduct(1L));
  }

  @Test(expected = ProductNotFoundException.class)
  public void testGetProductNotFound() throws ProductNotFoundException {
    productDao.getProduct(null);
  }

  @Test
  public void testSaveNewProduct() throws ProductNotFoundException {
    Product product = productBuilder.setCode("test-code").setDescription("test-description").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(100).setImageUrl("test-url").createProduct();

    productDao.save(product);
    assertTrue(product.getId() >= 0);
    Product actual = productDao.getProduct(product.getId());
    assertEquals("test-code", actual.getCode());
  }

  @Test
  public void testSaveUpdateProduct() throws ProductNotFoundException {
    Product product = productBuilder.setCode("test-code").setDescription("test-description").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(100).setImageUrl("test-url").createProduct();
    productDao.save(product);
    Product productUpdate = productBuilder.setId(product.getId()).setCode("updated-code").setDescription("updated-description").setPrice(new BigDecimal(10)).setCurrency(usd).setStock(10).setImageUrl("updated-url").createProduct();
    productDao.save(productUpdate);
    Product actual = productDao.getProduct(product.getId());
    assertEquals("updated-code", actual.getCode());
  }

  @Test(expected = ProductNotFoundException.class)
  public void testDeleteProduct() throws ProductNotFoundException {
    Product product = productBuilder.setCode("test-code").setDescription("test-description").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(100).setImageUrl("test-url").createProduct();
    productDao.save(product);
    productDao.delete(product.getId());
    productDao.getProduct(product.getId());
  }
}
