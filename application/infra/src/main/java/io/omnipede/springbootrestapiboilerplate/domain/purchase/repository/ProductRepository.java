package io.omnipede.springbootrestapiboilerplate.domain.purchase.repository;

import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
    
}
