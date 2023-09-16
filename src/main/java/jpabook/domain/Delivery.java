package jpabook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    // +) 1대1 관계인 경우 FK의 위치가 무관하지만, 조회횟수 등을 고려했을 때 가장 데이터접근이 많은 쪽에 FK를 배치하는 것이 유용
    // (별도 필터링과정 없이 JOIN을 통해 값을 바로 가져올 수 있기 때문)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    /*
    <EnumType별 DB 내부 Enumerate 저장방식>
    ORDINARY: 각 값을 순차적으로 숫자 할당 후 할당값 저장 (-> 향후 enum값 중간에 값 추가시 문제발생 가능)
    STRING: 각 값을 문자열로 저장
     */
    private DeliveryStatus status;
}
