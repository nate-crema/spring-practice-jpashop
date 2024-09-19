package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // : 아래 class를 Spring Bean으로 등록
public class MemberRepository {

    @PersistenceContext // : Spring이 EntityManager를 생성하여 해당 `MemberRepository.em`에 자동으로 injection
    private EntityManager em;

    // +) EntityManagerFactory를 inject 받으려면
    // @PersistenceUnit
    // private EntityManagerFactory emf;

    public void save( Member member ) {
        em.persist( member ); //: member 객체를 저장
    }

    public Member findOne( Long id ) {
        return em.find( Member.class, id );
    }

    public List<Member> findAll() {
        List<Member> members = em.createQuery("select m from Member m ", Member.class)
                .getResultList();

        return members;
    }

    public List<Member> findByName( String name ) {
        return em.createQuery( "select m from Member m where m.name = :name", Member.class )
                .setParameter( "name", name )
                .getResultList();
    }
}
