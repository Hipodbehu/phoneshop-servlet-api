package com.es.phoneshop.web.listener;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.dao.ProductDao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.util.Currency;

public class ProductDemodataServletContextListener implements ServletContextListener {
  private ProductDao productDao;

  public ProductDemodataServletContextListener() {
    this.productDao = ArrayListProductDao.getInstance();
  }

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    boolean insertDemoData = Boolean.parseBoolean(servletContextEvent.getServletContext()
            .getInitParameter("insertDemoData"));
    if (insertDemoData) {
      saveSampleProducts();
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {

  }

  private void saveSampleProducts() {
    Currency usd = Currency.getInstance("USD");
    Product.ProductBuilder productBuilder = new Product.ProductBuilder();
    Product product = productBuilder.setCode("sgs").setDescription("Samsung Galaxy S").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg").createProduct();
    product.setPrice(BigDecimal.valueOf(123.33));
    product.setPrice(BigDecimal.valueOf(101));
    productDao.save(product);
    productDao.save(productBuilder.setCode("sgs2").setDescription("Samsung Galaxy S II").setPrice(new BigDecimal(200)).setCurrency(usd).setStock(0).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg").createProduct());
    productDao.save(productBuilder.setCode("sgs3").setDescription("Samsung Galaxy S III").setPrice(new BigDecimal(300)).setCurrency(usd).setStock(5).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg").createProduct());
    productDao.save(productBuilder.setCode("iphone").setDescription("Apple iPhone").setPrice(new BigDecimal(200)).setCurrency(usd).setStock(10).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg").createProduct());
    productDao.save(productBuilder.setCode("iphone6").setDescription("Apple iPhone 6").setPrice(new BigDecimal(1000)).setCurrency(usd).setStock(30).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg").createProduct());
    productDao.save(productBuilder.setCode("htces4g").setDescription("HTC EVO Shift 4G").setPrice(new BigDecimal(320)).setCurrency(usd).setStock(3).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg").createProduct());
    productDao.save(productBuilder.setCode("sec901").setDescription("Sony Ericsson C901").setPrice(new BigDecimal(420)).setCurrency(usd).setStock(30).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg").createProduct());
    productDao.save(productBuilder.setCode("xperiaxz").setDescription("Sony Xperia XZ").setPrice(new BigDecimal(120)).setCurrency(usd).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg").createProduct());
    productDao.save(productBuilder.setCode("nokia3310").setDescription("Nokia 3310").setPrice(new BigDecimal(70)).setCurrency(usd).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg").createProduct());
    productDao.save(productBuilder.setCode("palmp").setDescription("Palm Pixi").setPrice(new BigDecimal(170)).setCurrency(usd).setStock(30).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg").createProduct());
    productDao.save(productBuilder.setCode("simc56").setDescription("Siemens C56").setPrice(new BigDecimal(70)).setCurrency(usd).setStock(20).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg").createProduct());
    productDao.save(productBuilder.setCode("simc61").setDescription("Siemens C61").setPrice(new BigDecimal(80)).setCurrency(usd).setStock(30).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg").createProduct());
    productDao.save(productBuilder.setCode("simsxg75").setDescription("Siemens SXG75").setPrice(new BigDecimal(150)).setCurrency(usd).setStock(40).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg").createProduct());
  }
}
