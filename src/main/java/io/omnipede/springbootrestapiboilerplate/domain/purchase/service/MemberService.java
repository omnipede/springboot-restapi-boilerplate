package io.omnipede.springbootrestapiboilerplate.domain.purchase.service;

import io.omnipede.springbootrestapiboilerplate.domain.purchase.entity.Member;
import io.omnipede.springbootrestapiboilerplate.domain.purchase.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    /**
     * Member 를 생성하고 영속화하는 메소드
     * @param username Username of member
     */
    public Member createAndSaveMember(String username) {
        Member member = new Member(username);
        memberRepository.save(member);
        return member;
    }

    /**
     * Member 를 반환하는 메소드
     * @param id Member id
     * @return Member
     * @throws Exception 해당하는 멤버가 없을 경우 에러 던짐
     */
    public Member findMember(Long id) throws Exception {
        return memberRepository.findById(id).orElseThrow(() -> new Exception("Data not found"));
    }
}
