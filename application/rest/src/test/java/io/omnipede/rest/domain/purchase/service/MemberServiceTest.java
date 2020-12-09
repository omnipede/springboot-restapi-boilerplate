package io.omnipede.rest.domain.purchase.service;

import io.omnipede.rest.domain.purchase.entity.Member;
import io.omnipede.rest.domain.purchase.repository.MemberRepository;
import io.omnipede.rest.global.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
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
    public void findMember_NotFound() {
        // 없는 멤버를 찾을 경우, 에러 발생
        assertThrows(BusinessException.class, () -> {
           memberService.findMember(20000L);
        });
    }
}