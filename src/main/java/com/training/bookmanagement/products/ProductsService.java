package com.training.bookmanagement.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProductsService {

    @Autowired
    private ProductsRepository productDao;

    public Iterable<Product> getAllProducts() {
        return productDao.findAll();
    }

    @Transactional
    public Product saveProducts(Product product) {
        return productDao.save(product);
    }
}
