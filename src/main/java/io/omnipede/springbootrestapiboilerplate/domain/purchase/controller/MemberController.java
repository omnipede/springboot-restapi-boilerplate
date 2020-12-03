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
import java.util.*;

@RestController
@Validated
@RequestMapping("/api/v1")
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService, PurchaseService purchaseService) {
        this.memberService = memberService;
    }

    /**
     * 회원 정보 가져오기
     */
    @RequestMapping(method=RequestMethod.GET, value="/member/{id}", headers="accept=application/json")
    Map<String, Object> profile(@Valid ProfileDTO dto) {
        Long id = dto.getId();
        Member member = memberService.findMember(id);
        List<MemberProduct> purchaseList = member.getMemberProducts();

        List<Map<String, Object>> purchaseListDTO = new ArrayList<>();
        purchaseList.forEach((mp) -> {
            Product purchasedProduct = mp.getProduct();
            Map<String, Object> purchaseDTO = new HashMap<>();
            purchaseDTO.put("name", purchasedProduct.getName());
            purchaseListDTO.add(purchaseDTO);
        });

        // 응답
        Map<String, Object> responseBodyDTO = new LinkedHashMap<>();
        responseBodyDTO.put("id", member.getId());
        responseBodyDTO.put("username", member.getUsername());
        responseBodyDTO.put("purchased", purchaseListDTO);
        return responseBodyDTO;
    }
}
