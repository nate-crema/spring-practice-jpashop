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

    protected Address() {}
    //: Entity나 @Embeddable 객체는 반드시 기본생성자가 필요
    // ㄴ JAVA Reflection API에서 가져올 수 없는 정보 중 하나가 'constructor의 필드정보'
    // ㄴ Reflection API는 Proxy 등의 기술 지원을 위해 사용되는 API로, 객체를 제어하기 위해 인자가 없는 기본생성자로 객체를 생성함
    // ㄴ 만약 기본생성자가 없는경우 JPA의 구현 상 문제가 발생할 수 있기 때문에 기본생성자를 작성하여 추가해줘야 함
    // ㄴ 다만, public으로 사용시 의도치 않은 경우에도 사용될 수 있으므로, protected로 사용할 것

    public Address(String city, String street, int zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
