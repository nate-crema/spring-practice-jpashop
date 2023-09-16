package jpabook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jpabook.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // = protected OrderItem() {} (=개발 시 타 class에서 생성자를 사용한 객체생성을 막는 역할)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문가격
    private int count; // 주문수량

    // ==== 생성 메서드
    public static OrderItem createOrderItem( Item item, int orderPrice, int count ) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem( item );
        orderItem.setOrderPrice( orderPrice );
        orderItem.setCount( count );

        item.removeStock( count );
        return orderItem;
    }

    // ==== 비즈니스 로직
    public void cancel() {
        this.getItem().addStock( this.getCount() );
    }

    // ==== 조회 로직
    public int getTotalPrice() {
        return this.getOrderPrice() * this.getCount();
    }
}
