package jpabook;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpashopApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpashopApplication.class, args);
    }

    @Bean
    Hibernate5JakartaModule hibernate5JakartaModule() {
        /*
            Hibernate5JakartaModule 기본전략 사용
            : 지연로딩은 전부 무시
        */
        return new Hibernate5JakartaModule();

        /*
            지연로딩을 전부 조회한 뒤 데이터를 출력하도록 설정
            : json 생성 시점에 Lazy Loading을 강제로 실행 후 데이터 출력
        */
//        Hibernate5JakartaModule hibernate5JakartaModule = new Hibernate5JakartaModule();
//        hibernate5JakartaModule.configure(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING, true);
//        return hibernate5JakartaModule;
    }
}