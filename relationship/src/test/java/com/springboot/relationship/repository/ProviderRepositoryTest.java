package com.springboot.relationship.repository;

import com.springboot.relationship.data.entity.Producer;
import com.springboot.relationship.data.entity.Product;
import com.springboot.relationship.data.entity.Provider;
import com.springboot.relationship.data.repository.ProductRepository;
import com.springboot.relationship.data.repository.ProviderRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class ProviderRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProviderRepository providerRepository;

    @Test
    void relationshipTest(){
        Provider provider = new Provider();

        provider.setName("ㅇㅇ상사");

        providerRepository.save(provider);

        Product product1 = new Product();

        product1.setName("펜");
        product1.setPrice(2000);
        product1.setStock(100);
        product1.setProvider(provider);

        Product product2 = new Product();
        product2.setName("가방");
        product2.setPrice(20000);
        product2.setStock(100);
        product2.setProvider(provider);

        Product product3 = new Product();
        product3.setName("노트");
        product3.setPrice(3000);
        product3.setStock(100);
        product3.setProvider(provider);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        List<Product> products = providerRepository.findById(provider.getId()).get().getProductList();

        for(Product product : products){
            System.out.println(product);
        }

        //테스트
//        System.out.println("product : " + productRepository.findById(1L).orElseThrow(RuntimeException::new));
//        System.out.println("product : " + productRepository.findById(1L).orElseThrow(RuntimeException::new).getProvider());

    }

    @Test
    void relationshipTest1(){
        Provider provider = new Provider();

        provider.setName("ㅇㅇ물산");

        providerRepository.save(provider);

        Product product = new Product();

        product.setName("가위");
        product.setPrice(5000);
        product.setStock(500);
        product.setProvider(provider);

        productRepository.save(product);

        //테스트
        System.out.println("product : " + productRepository.findById(1L).orElseThrow(RuntimeException::new));
        System.out.println("product : " + productRepository.findById(1L).orElseThrow(RuntimeException::new).getProvider());

    }

    @Test
    void cascadeTest(){
        Provider provider = savedProvide("새로운 공급업체");

        Product product1 = saveProduct("상품1", 1000, 1000);
        Product product2 = saveProduct("상품2", 500, 1500);
        Product product3 = saveProduct("상품2", 750, 500);

        // 연관관계 설정
        product1.setProvider(provider);
        product2.setProvider(provider);
        product3.setProvider(provider);

        provider.getProductList().addAll(Lists.newArrayList(product1, product2, product3));

        providerRepository.save(provider);

    }

    private Provider savedProvide(String name){
        Provider provider = new Provider();
        provider.setName(name);

        return provider;
    }

    private Product saveProduct(String name, Integer price, Integer stock) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);

        return product;
    }

    @Test
    @Transactional
    void orphanRemovalTest(){
        Provider provider = savedProvide("새로운 공급업체");

        Product product1 = saveProduct("상품1", 1000, 1000);
        Product product2 = saveProduct("상품2", 500, 1500);
        Product product3 = saveProduct("상품2", 750, 500);

        product1.setProvider(provider);
        product2.setProvider(provider);
        product3.setProvider(provider);

        provider.getProductList().addAll(Lists.newArrayList(product1, product2, product3));

        providerRepository.saveAndFlush(provider);
        providerRepository.findAll().forEach(System.out::println);
        productRepository.findAll().forEach(System.out::println);

        Provider foundProvider = providerRepository.findById(1L).get();
        foundProvider.getProductList().remove(0);

        providerRepository.findAll().forEach(System.out::println);
        productRepository.findAll().forEach(System.out::println);
    }
}
