package io.omnipede.springbootrestapiboilerplate.domain.purchase.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Member;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.MemberProduct;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Product;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.repository.MemberProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PurchaseServiceTest {

    final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MemberProductRepository memberProductRepository;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ProductService productService;

    @Autowired
    private MemberService memberService;

    @Test
    public void purchase() throws Exception {
        Member member = memberService.createAndSaveMember("김길동");
        Product product = productService.createAndSaveProduct("냉장고");
        // Member purchased a product
        purchaseService.purchase(member, product);
        // 데이터가 정상적으로 들어갔는지 확인
        List<MemberProduct> memberProductList = member.getMemberProducts();
        memberProductList.forEach((memberProduct) -> {
            Member m = memberProduct.getMember();
            Product p = memberProduct.getProduct();
            assertEquals(m.getUsername(), member.getUsername());
            assertEquals(p.getName(), product.getName());
        });
    }
}