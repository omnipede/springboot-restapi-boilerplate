package io.omnipede.rest.domain.purchase.service;

import io.omnipede.rest.domain.purchase.entity.Member;
import io.omnipede.rest.domain.purchase.repository.MemberRepository;
import io.omnipede.rest.global.exception.BusinessException;
import io.omnipede.rest.global.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

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
     */
    public Member findMember(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_EXISTS));
    }
}
