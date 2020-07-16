package io.omnipede.springbootrestapiboilerplate.domain.purchase.service;

import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Member;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.MemberProduct;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Product;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.repository.MemberProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {
    @Autowired
    private MemberProductRepository memberProductRepository;

    /**
     * 사용자의 구매기록을 추가하는 메소드
     * @param member 사용자
     * @param product 사용자가 구매한 상품
     */
    public void purchase(Member member, Product product) {
        MemberProduct memberProduct = new MemberProduct(member, product);
        // memberProduct.getMember().getMemberProducts().add(memberProduct);
        // memberProduct.getProduct().getMemberProducts().add(memberProduct);
        memberProductRepository.save(memberProduct);
    }


}
