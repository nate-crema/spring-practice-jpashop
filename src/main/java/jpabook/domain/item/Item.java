package jpabook.domain.item;

import jakarta.persistence.*;
import jpabook.domain.Category;
import jpabook.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // Entity의 상속관계에 대한 전략구성 명세
/*
<전략별 구현방식>
JOINED: 정규화된 방식으로 구현; 출력 시 JOIN하여 출력되고, 입력 시 2번의 insert문이 db로 요청됨
TABLE_PER_CLASS: 상속받는 각 클래스에 대한 table 별도 생성
SINGLE_TABLE: 한개의 테이블에 모든 필드를 다 박아버리는 방식
 */

@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // business logic

    /**
     * stock 증가
     * @param quantity 증가 수량
     */
    public void addStock( int quantity ) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     * @param quantity 감소 수량
     */
    public void removeStock ( int quantity ) {
        int restStock = this.stockQuantity - quantity;
        if ( restStock < 0 )
            throw new NotEnoughStockException("need more stock");

        this.stockQuantity = restStock;
    }
}
