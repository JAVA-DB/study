package com.springboot.advanced_jpa.data.dao.impl;

import com.springboot.advanced_jpa.data.dao.ProductDAO;
import com.springboot.advanced_jpa.data.entity.Product;
import com.springboot.advanced_jpa.data.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class ProductDAOImpl implements ProductDAO {

    private final ProductRepository productRepository;

    @Autowired
    public ProductDAOImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Product insertProduct(Product product) {
        Product saveProduct = productRepository.save(product);

        return saveProduct;
    }

    @Override
    public Product selectProduct(Long number) {
        Product selectProduct = productRepository.getById(number);

        return selectProduct;
    }

    @Override
    public Product updateProductName(Long number, String name) throws Exception {
        Optional<Product> selectdProduct = productRepository.findById(number);

        Product updatedProduct;
        if(selectdProduct.isPresent()) {
            Product product = selectdProduct.get();

            product.setName(name);
//            product.setUpdateAt(LocalDateTime.now());

            updatedProduct = productRepository.save(product);
        } else {
          throw new Exception();
        }

        return updatedProduct;
    }

    @Override
    public void delecteProduct(Long number) throws Exception {
        Optional<Product> selectedProduct = productRepository.findById(number);

        if (selectedProduct.isPresent()){
            Product product = selectedProduct.get();

            productRepository.delete(product);
        } else {
            throw new Exception();
        }
    }
}
