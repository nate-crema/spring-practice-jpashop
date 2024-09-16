package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// InheritanceType.JOINED: 정규화된 형태
// InheritanceType.SINGLE_TABLE: 1개의 Table에 본 Class를 상속받은 Class의 필드를 모두 저장
// InheritanceType.TABLE_PER_CLASS: 본 Class를 상속받은 Class마다 Table을 각각 생성
@DiscriminatorColumn(name = "dtype") //: 본 Class를 상속받는 Class를 서로 구분할 수 있도록 해주는 Column의 이름을 지정
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    // ㄴ> mappedBy에는 상대 'Entity와 mapping될 field명'이 들어가야 하므로
    // | 연결되는 column명이 다르더라도 이와는 무관하게 field명으로 작성
    private List<Category> categories = new ArrayList<>();
}
