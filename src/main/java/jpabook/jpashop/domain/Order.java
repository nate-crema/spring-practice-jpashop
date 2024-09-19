package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "orders" ) //: Table명을 직접 지정하기 위해 사용
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column( name = "order_id" )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //: '현재 Entity 기준' 여러개가 상대의 1개에 대응됨을 명시
    @JoinColumn( name="member_id" ) //: SQL에서 join 할 column 이름을 지정
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    // : [option: 'cascade'] Order Entity가 저장될 때, orderItems에게도 명령이 전파되도록 하는 것
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
