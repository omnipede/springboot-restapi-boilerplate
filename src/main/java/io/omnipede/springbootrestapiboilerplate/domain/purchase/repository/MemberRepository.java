package io.omnipede.springbootrestapiboilerplate.domain.purchase.repository;

import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {

}
