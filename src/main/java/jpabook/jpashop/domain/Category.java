package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable( //: 특정 Table과 join시켜서 데이터를 가져오기 위해 사용
    // ㄴ 불가피하게 다대다관계를 표현하기 위해 db 상에서 연결용 테이블(=Join Table)을 생성 시 사용
        name = "category_item", //: 'category_item'이라는 중간 연결용 테이블에 접근
        joinColumns = @JoinColumn( name = "category_id" ), //: 'category_id'라는 필드를 기준으로 현재 Entity에서 참조하여 join
        inverseJoinColumns = @JoinColumn( name = "item_id") //: 상대 Entity에서 참조할 때 기준으로 할 'item_id'를 명시
    )
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child;

    // ====| 연관관계 편의 메서드 |====
    public void addChildCategory( Category category ) {
        this.child.add( category );
        category.setParent( this );
    }

}
