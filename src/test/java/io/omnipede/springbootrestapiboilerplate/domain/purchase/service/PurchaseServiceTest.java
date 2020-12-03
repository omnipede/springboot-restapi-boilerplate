package io.omnipede.springbootrestapiboilerplate.domain.purchase.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Member;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.MemberProduct;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Product;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.repository.MemberProductRepository;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.repository.MemberRepository;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.repository.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PurchaseServiceTest {

    final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MemberProductRepository memberProductRepository;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void purchase() throws Exception {
        Member member = memberRepository.save(new Member("김길동"));
        Product product = productRepository.save(new Product("냉장고"));
        // Member purchased a product
        MemberProduct memberProduct = purchaseService.purchase(member, product);
        // 데이터가 정상적으로 들어갔는지 확인
        MemberProduct mp = memberProductRepository.findById(memberProduct.getId()).orElseGet(() -> null);
        assertNotEquals(mp, null);
    }

    @Test
    public void findByMember() throws Exception {
        Member member = memberRepository.save(new Member("김길동"));
        Product product = productRepository.save(new Product("냉장고"));

        purchaseService.purchase(member, product);

        memberProductRepository.save(new MemberProduct(member, product));

        List<MemberProduct> memberProductList = purchaseService.findByMember(member);
        assertNotEquals(memberProductList.size(), 0);

        memberProductList.forEach((mp)  -> {
            assertEquals(mp.getMember().getId(), member.getId());
            assertEquals(mp.getProduct().getId(), product.getId());
        });
    }
}