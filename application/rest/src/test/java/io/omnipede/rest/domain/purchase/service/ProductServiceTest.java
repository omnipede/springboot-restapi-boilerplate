package io.omnipede.rest.domain.purchase.service;

import io.omnipede.rest.domain.purchase.entity.Product;
import io.omnipede.rest.domain.purchase.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Test
    public void addProduct() throws Exception {
        Product product = productService.createAndSaveProduct("사과");
        // Get entity from repository
        Product productInsideDB = productRepository.findById(product.getId()).orElseThrow(() -> new Exception("Data not found"));
        // Check whether product inside database and product is equals
        assertEquals(productInsideDB.getId(), product.getId());
        assertEquals(productInsideDB.getName(), product.getName());
    }
}