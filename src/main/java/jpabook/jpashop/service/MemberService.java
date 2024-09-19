package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

@Transactional(readOnly = true)
// : 데이터의 변경이 Transaction 안에서 일어나도록 하기 위해 이 class를 transaction에 추가
// [option: 'readOnly'] Spring 내부에서 readOnly 옵션에 따라 자동으로 최적화
// ㄴ 데이터의 변경작업이 되지 않는 대신, 성능이 최적화됨
public class MemberService {

//    @Autowired // : Spring이 Bean에 등록되어있는 memberRepository auto-wiring (=Field Injection)
    private final MemberRepository memberRepository;

    // Constructor Injection
    // : 테스트 등 개발자가 임의사유로 repository를 Injection해야할 때, injection 수단을 제공하고자 사용하는 방식
    // ㄴ Field Injection은 개발자가 테스트를 위해 접근할 수 없다는 점, Setter Injection은 Runtime에서 repository가 바뀔 수 있다는 점을 보완한 방식

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     * @param member
     * @return
     */
    @Transactional // : readOnly 옵션이 적용된 @Transactional Annotation의 영향을 받지 않기 위해 기입
    public Long join( Member member ) {

        // 중복회원여부 확인
        validateDuplicateMember( member );

        // 사용자정보 저장
        memberRepository.save( member );
        return member.getId();
    }

    private void validateDuplicateMember( Member member ) {
        List<Member> prejoinedMembers = memberRepository.findByName( member.getName() );
        if ( !prejoinedMembers.isEmpty() )
            throw new IllegalStateException("이미 존재하는 회원입니다");
    }

    // 회원 전체조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne( Long id ) {
        return memberRepository.findOne( id );
    }
}
