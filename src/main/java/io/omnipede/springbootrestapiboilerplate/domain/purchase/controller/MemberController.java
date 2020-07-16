package io.omnipede.springbootrestapiboilerplate.domain.purchase.controller;

import io.omnipede.springbootrestapiboilerplate.domain.purchase.dto.MemberSignin;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Member;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.service.MemberService;
import io.omnipede.springbootrestapiboilerplate.domain.topic.TopicDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * 회원가입 (멤버 추가)
     */
    @RequestMapping(method=RequestMethod.POST, value="/member/signin", headers="accept=application/json")
    public @ResponseBody
    Member signin(@RequestBody @Valid MemberSignin dto) {
        String username = dto.getUsername();
        return memberService.createAndSaveMember(username);
    }

    /**
     * 회원 정보 가져오기
     */
    @RequestMapping(method=RequestMethod.GET, value="/member/{id}", headers="accept=application/json")
    Member profile(@PathVariable Long id) {
        return memberService.findMember(id);
    }
}
