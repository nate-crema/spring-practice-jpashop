package jpabook.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    // : 연관관계의 주인이 아닐 때, 연관관계의 주인의 어떤 column에서 매핑되는 값인지 명시해주는 Annotation
    // = 해당 필드를 수정할 때, 변경내용이 db에 전달되지 않음
    private List<Order> orders = new ArrayList<>();
}
