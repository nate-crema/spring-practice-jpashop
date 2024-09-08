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

    @ManyToOne //: '현재 Entity 기준' 여러개가 상대의 1개에 대응됨을 명시
    @JoinColumn( name="member_id" ) //: SQL에서 join 할 column 이름을 지정
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
