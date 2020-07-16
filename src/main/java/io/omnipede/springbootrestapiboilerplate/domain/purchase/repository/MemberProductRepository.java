package io.omnipede.springbootrestapiboilerplate.domain.purchase.repository;

import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.MemberProduct;
import org.springframework.data.repository.CrudRepository;

public interface MemberProductRepository extends CrudRepository<MemberProduct, Long> {
    
}
