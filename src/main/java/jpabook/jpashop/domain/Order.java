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

    // ====| 연관관계 편의 메서드 |====
    // : 양방향 연관관계에서 다른쪽에 데이터가 추가되지 않는 문제를 방지하기 위해 사용.
    // ㄴ 일반적으로 연관관계의 주인쪽에 연관관계 편의 메서드를 작성

    public void setMember( Member member ) {
        this.member = member;
        member.getOrders().add( this );
    }

    public void setOrderItem( OrderItem orderItem ) {
        this.orderItems.add( orderItem );
        orderItem.setOrder( this );
    }

    public void setDelivery( Delivery delivery ) {
        this.delivery = delivery;
        delivery.setOrder( this );
    }
}
