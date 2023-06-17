package jpabook.service;

import jpabook.domain.Member;
import jpabook.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // for performance improvement
@RequiredArgsConstructor
public class MemberService {

    // final annotation 추가 => 컴파일 시점에 생성자로 데이터가 설정되는지 확인 가능
    private final MemberRepository memberRepository;

//    @Autowired
//    // setter injection; 테스트 등의 목적으로 repository를 변경해야 할 때, 필드에서 바로 설정되지 않고 중간에 setter를 한번 거치도록 함
//    // 장점: 개발자가 repository를 변경할 수 있도록 구현
//    // 단점: 서비스 런타임 중 변경될 가능성이 있음 (실서비스에서 repository를 변경하는 가능성 거의 없음)
//    // => 생성자를 통해 설정
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

//    @Autowired // 생성자가 1개만 있는경우 Autowired를 생략하여도 spring에서 injection 헤줌
    // generator injection
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    } // => lombok의 AllArgsConstructor & RequiredArgsConstructor 자동생성 시킬 수 있음

    /**
     * 회원조회
     * @param member
     * @return
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicationMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicationMember(Member member) {
        List<Member> findMembers = memberRepository.findMemberByName(member.getName());
        if ( !findMembers.isEmpty() )
            throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    // 회원 전체조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
