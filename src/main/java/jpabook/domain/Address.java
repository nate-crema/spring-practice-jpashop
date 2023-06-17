package jpabook.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
// Embeddable spec: 기본 생성자를 public이나 protected로 설정해야 함
// => JPA 구현 라이브러리에서 객체 생성 시 리플랙선, 프록시 등의 기술을 사용하여 생성하기 떄문
@Getter
public class Address { // 값타입 -> Setter를 제공하지 않아야 함

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
        // => protected로 설정 시 상속관계까지만 해당 생성자를 호출할 수 있으므로 설정;
        // public으로 설정 시 무심코 생성하여 데이터가 없는 상태로 개발될 수 있기 때문
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
