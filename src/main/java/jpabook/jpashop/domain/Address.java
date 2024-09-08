package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Embeddable
//: 공통된 필드를 분리하고 싶을 때 사용. (for '코드 재사용' & '높은 응집도')
// => 실제 Table은 생성되지 않음
// ㄴ jpashop 예제 상 Value Type 이므로 별도 Class 분리 후 공통필드로서 사용
@Getter @Setter
public class Address {
    private String city;
    private String street;
    private int zipcode;
}
