package jpabook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // 데이터를 가져올 때 참조할 column명 지정
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    // +) cascade 사용조건: 한개의 Entity에서만 사용되는 경우
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간
    // LocalDateTime: 기존 Date와는 다르게 Hibernate에서 자동으로 Annotation 없이 매핑해주는 class
    // (Date형의 경우 Annotation을 사용하여 별도로 매핑해주는 과정이 필요함)

    private OrderStatus status; // 주문상태

    // ==== 연관관계 편의 메서드
    // : 양방향 관계가 있는 객체 간 변경사항이 양측 모두 반영되도록 하기 위한 메서드
    public void setMember ( Member member ) {
        this.member = member;
        member.getOrders().add( this );
    }

    public void addOrderItem ( OrderItem orderItem ) {
        orderItems.add( orderItem );
        orderItem.setOrder( this );
    }

    public void setDelivery ( Delivery delivery ) {
        this.delivery = delivery;
        delivery.setOrder( this );
    }
    
    // ==== 생성 메서드
    // : 해당 객체를 생성하는 과정이 복잡한 경우, 이를 담당하여 생성해주는 메서드

    /**
     * 주문객체 생성 with relation setting
     * @param member
     * @param delivery
     * @param orderItems
     * @return
     */
    public static Order createOrder( Member member, Delivery delivery, OrderItem... orderItems ) {
        Order order = new Order();
        order.setMember( member );
        order.setDelivery( delivery );
        for ( OrderItem orderItem: orderItems )
            order.addOrderItem(orderItem);
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    // ==== 비즈니스 로직
    public void cancel() {
        if ( getDelivery().getStatus() == DeliveryStatus.COMP )
            throw new IllegalStateException("이미 배송된 상품으로 취소가 불가합니다");

        this.setStatus(OrderStatus.CANCEL);
        for ( OrderItem orderItem: orderItems )
            orderItem.cancel();
    }

    // ==== 조회 로직

    /**
     * 전체 주문가격 조회
     * @return
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for ( OrderItem orderItem: orderItems )
            totalPrice += orderItem.getTotalPrice();

        return totalPrice;
    }
}
