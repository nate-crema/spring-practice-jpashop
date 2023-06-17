package jpabook.domain;

import jakarta.persistence.*;
import jpabook.domain.item.Item;
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
    // 다대다 관계를 db table 만으로는 표현이 불가능하기 때문
    // => 1대다 -> 다대1로 연결하기 위한 중간테이블 필요
    @JoinTable(name = "category_item",
        joinColumns = @JoinColumn(name = "category_id"),
        inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent") // = parent 필드를 주인으로 설정 (=parent 필드의 값을 통해 현재 필드의 정보를 정의함)
    private List<Category> child = new ArrayList<>();

    // 연관관계 편의 메서드
    public void addChildCategory(Category category) {
        this.child.add(category);
        category.setParent(this);
    }
}
