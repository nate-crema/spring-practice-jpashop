package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    // : 연관관계의 주인이 아니기때문에, Hibernate 5 버전 이후에서의 설계전략 상 LAZY 조회가 불가능항
    private Order order;

    private Address address;

    @Enumerated(EnumType.STRING)
    // @Enumerated: enum값으로 필드값을 설정 시, 해당 값을 DB에 저장할 방식을 지정
    // EnumType.ORDINAL: 순서대로 숫자값을 매칭하여 저장
    // EnumType.STRING: 문자열 값으로 저장
    private DeliveryStatus deliveryStatus;
}
