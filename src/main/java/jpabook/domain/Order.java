package jpabook.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // 데이터를 가져올 때 참조할 column명 지정
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간
    // LocalDateTime: 기존 Date와는 다르게 Hibernate에서 자동으로 Annotation 없이 매핑해주는 class
    // (Date형의 경우 Annotation을 사용하여 별도로 매핑해주는 과정이 필요함)

    private OrderStatus status; // 주문상태

    // 연관관계 편의 메서드
    // : 양방향 관계가 있는 객체 간 변경사항이 양측 모두 반영되도록 하기 위한 메서드
    public void setMember ( Member member ) {
        this.member = member;
        member.getOrders().add( this );
    }

    public void setOrderItems ( OrderItem orderItems ) {
        this.orderItems.add( orderItems );
        orderItems.setOrder( this );
    }

    public void setDelivery ( Delivery delivery ) {
        this.delivery = delivery;
        delivery.setOrder( this );
    }
}
