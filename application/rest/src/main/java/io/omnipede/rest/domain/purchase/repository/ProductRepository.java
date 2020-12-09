package io.omnipede.rest.domain.purchase.repository;

import io.omnipede.rest.domain.purchase.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
    
}
