package io.omnipede.springbootrestapiboilerplate.domain.purchase.controller;

import io.omnipede.springbootrestapiboilerplate.domain.purchase.dto.PurchaseDTO;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Member;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Product;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.service.MemberService;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.service.ProductService;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PurchaseController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PurchaseService purchaseService;

    @RequestMapping(method= RequestMethod.POST, value="/purchase", headers="accept=application/json")
    public @ResponseBody
    Map<String, Object> purchase(@Valid @RequestBody PurchaseDTO dto) {
        Long memberId = dto.getMemberId();
        String productName = dto.getProductName();

        // 멤버를 찾음 (없으면 404 에러)
        Member member = memberService.findMember(memberId);
        // 상품 생성
        Product product = productService.createAndSaveProduct(productName);
        // 상품 구입
        purchaseService.purchase(member, product);
        // 응답 생성
        Map<String, Object> responseBodyDTO = new HashMap<>();
        responseBodyDTO.put("status", 200);
        return responseBodyDTO;
    }
}
