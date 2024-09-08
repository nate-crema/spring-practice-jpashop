package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B")
// @DiscriminatorValue: 상속받은 Class에서 Inheritance Strategy가 'SINGLE_TABLE'인 경우
// | 상속구현체 별 구분이 되지 않으므로, 이를 구분할 수 있는 Table에서의 구분값을 설정해줌 (부모 class에서 지정된 column의 값으로 'B'가 설정됨)
@Getter @Setter
public class Book extends Item {

    private String author;
    private String isbn;
}
