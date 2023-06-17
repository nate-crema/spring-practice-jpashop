package jpabook.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B") // DB 입장에서 상속받는 클래스 별로 값을 구분하는 기준값
@Getter @Setter
public class Book extends Item {

    private String author;
    private String isbm;
}
