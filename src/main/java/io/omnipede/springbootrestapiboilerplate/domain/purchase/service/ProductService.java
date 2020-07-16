package io.omnipede.springbootrestapiboilerplate.domain.purchase.service;

import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Product;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    /**
     * 상품을 데이터베이스에 추가하는 메소드
     * @param name 새로 추가할 상품의 이름
     */
    public Product createAndSaveProduct(String name) {
        Product product = new Product(name);
        // Save product
        productRepository.save(product);
        return product;
    }
}
