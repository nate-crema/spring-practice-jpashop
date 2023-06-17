package jpabook.service;

import jakarta.persistence.EntityManager;
import jpabook.domain.Member;
import jpabook.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
// Transaction에서 commit하지 않고 바로 rollback => 실제 테스트 시에는 insert query가 실행되지 않음
// = 영속성 context가 flush하지 않음
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    @Rollback(false)
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("Kim");

        // when
        Long memberId = memberService.join(member);

        // then
        em.flush();
        assertEquals( member, memberService.findOne(memberId) );
    }

    @Test()
    public void 중복_회원_예외() throws Exception {
        // given
        String test_name = "TEST_NAME";
        Member member1 = new Member();
        member1.setName( test_name );
        memberService.join( member1 );

        // when
        Member member2 = new Member();
        member2.setName( test_name );

        // then
        assertThrows( IllegalStateException.class, () -> {
            memberService.join(member2);
        } );
    }
}