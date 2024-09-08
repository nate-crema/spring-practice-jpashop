package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Entity //: JPA에서 아래 class를 Entity로 관리하도록 지정하는 역할
@Getter @Setter
// @Getter / @Setter: lombok에서 자동으로 getter/setter를 추가하도록 설정
public class Member {

    @Id @GeneratedValue
    // @Id: 식별자 필드; Entity의 field와 Table의 기본 키를 매핑하는 역할
    // @GeneratedValue: 필드값을 자동생성하도록 명시
    @Column(name="member_id") //: Column명을 직접 지정하기 위해 사용
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    // @OneToMany: '현재 Entity 기준' 1개가 여러개에 대응됨을 명시
    // ㄴ mappedBy: 연관관계의 주인이 아닐 때, 상대 Entity의 어떤 필드를 주인으로 섬길 것인지 설정
    //     ㄴ> orders.add()를 한다고 하여 SQL이 호출될 때 Member Table을 변경하는 query로 날아가지 않음
    private List<Order> orders = new ArrayList<>();
}
