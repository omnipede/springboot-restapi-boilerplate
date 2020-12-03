package io.omnipede.springbootrestapiboilerplate.domain.purchase.controller;

import io.omnipede.springbootrestapiboilerplate.domain.purchase.dto.ProfileDTO;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Member;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.MemberProduct;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Product;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.service.MemberService;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.service.PurchaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/api/v1")
public class MemberController {

    private MemberService memberService;
    private PurchaseService purchaseService;

    @Autowired
    public MemberController(MemberService memberService, PurchaseService purchaseService) {
        this.memberService = memberService;
        this.purchaseService = purchaseService;
    }

    /**
     * 회원 정보 가져오기
     */
    @RequestMapping(method=RequestMethod.GET, value="/member/{id}", headers="accept=application/json")
    Map<String, Object> profile(@Valid ProfileDTO dto) {
        Long id = dto.getId();
        Member member = memberService.findMember(id);

        // Member 가 구입한 상품 목록을 반환함
        List<MemberProduct> memberProduct = purchaseService.findByMember(member);
        List<Map<String, Object>> productJsonList = new ArrayList<>();
        memberProduct.forEach((mp) -> {
            Product product = mp.getProduct();
            Map<String, Object> productJson = new HashMap<>();
            productJson.put("id", product.getId());
            productJson.put("name", product.getName());
            productJson.put("createdAt", product.getCreatedAt());
            productJson.put("updatedAt", product.getUpdatedAt());
            productJsonList.add(productJson);
        });
        // 응답
        Map<String, Object> responseBodyDTO = new HashMap<String, Object>();
        responseBodyDTO.put("id", member.getId());
        responseBodyDTO.put("username", member.getUsername());
        responseBodyDTO.put("purchased", productJsonList);
        return responseBodyDTO;
    }
}
