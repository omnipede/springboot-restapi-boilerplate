package io.omnipede.springbootrestapiboilerplate.domain.purchase.service;

import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Member;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Test
    public void createAndSaveMember() throws Exception {
        Member member = memberService.createAndSaveMember("서현규");
        // Get entity from repository
        Member memberInsideDB = memberRepository.findById(member.getId()).orElseThrow(() -> new Exception("Data not found"));
        // Check whether member inside database and member is equals
        assertEquals(memberInsideDB.getId(), member.getId());
        assertEquals(memberInsideDB.getUsername(), member.getUsername());
    }

    @Test
    public void findMember() {
        try {
            // 존재하지 않는 멤버를 찾는 경우
            Member member = memberService.findMember(20000L);
            fail("Exception should be thrown");
        } catch (Exception exception) {
            assertTrue(true);
        }
    }
}