package io.omnipede.springbootrestapiboilerplate.domain.purchase.controller;

import io.omnipede.springbootrestapiboilerplate.domain.purchase.dto.MemberSignin;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Member;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.MemberProduct;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Product;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.service.MemberService;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PurchaseService purchaseService;

    /**
     * 회원가입 (멤버 추가)
     */
    @RequestMapping(method=RequestMethod.POST, value="/member/signin", headers="accept=application/json")
    public @ResponseBody
    Map<String, Object> signin(@RequestBody @Valid MemberSignin dto) {
        String username = dto.getUsername();
        // 멤버 추가 및 영속화
        memberService.createAndSaveMember(username);
        // 응답
        Map<String, Object> responseBodyDTO = new HashMap<>();
        responseBodyDTO.put("status", 200);
        return responseBodyDTO;
    }

    /**
     * 회원 정보 가져오기
     */
    @RequestMapping(method=RequestMethod.GET, value="/member/{id}", headers="accept=application/json")
    Map<String, Object> profile(@PathVariable Long id) {
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
