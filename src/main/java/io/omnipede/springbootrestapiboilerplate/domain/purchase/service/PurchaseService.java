package io.omnipede.springbootrestapiboilerplate.domain.purchase.service;

import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Member;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.MemberProduct;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Product;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.repository.MemberProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
    private MemberProductRepository memberProductRepository;

    @Autowired
    public PurchaseService(MemberProductRepository memberProductRepository) {
        this.memberProductRepository = memberProductRepository;
    }

    /**
     * 사용자의 구매기록을 추가하는 메소드
     * @param member 사용자
     * @param product 사용자가 구매한 상품
     * @return 구매 기록
     */
    public MemberProduct purchase(Member member, Product product) {
        MemberProduct memberProduct = new MemberProduct(member, product);
        return memberProductRepository.save(memberProduct);
    }
}
