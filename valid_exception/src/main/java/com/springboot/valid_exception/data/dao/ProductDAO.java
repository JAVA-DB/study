package com.springboot.valid_exception.data.dao;

import com.springboot.valid_exception.data.entity.Product;

public interface ProductDAO {
    Product insertProduct(Product product);

    Product selectProduct(Long number);

    Product updateProductName(Long number, String name) throws Exception;

    void delecteProduct(Long number) throws Exception;
}
