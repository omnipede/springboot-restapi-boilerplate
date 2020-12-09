package io.omnipede.rest.domain.purchase.repository;

import io.omnipede.rest.domain.purchase.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {

}
