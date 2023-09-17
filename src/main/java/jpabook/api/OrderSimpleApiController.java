package jpabook.api;

import jpabook.domain.Order;
import jpabook.repository.OrderRepository;
import jpabook.repository.OrderSearch;
import jpabook.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    /**
     * @deprecated DTO 미적용
     * @return
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for ( Order order: all ) {
            order.getMember().getName();
            order.getDelivery().getAddress();
        }
        return all;
    }

    /**
     * @deprecated 요청하는 쿼리의 횟수가 너무 많음 (총 3개의 table에서 각 주문건당 2n번 조회)
     * @return
     */
    @GetMapping("/api/v2/simple-orders")
    public List<OrderSimpleQueryDto> orderV2() {
        return orderRepository.findAll(new OrderSearch()).stream()
                .map( OrderSimpleQueryDto::new )
//              ==  .map( v -> new SimpleOrderDto( v ) )
                .collect( Collectors.toList() );
    }

    /**
     * @deprecated 데이터를 불러온 뒤 DTO형식 재가공으로 인한 서버 메모리사용량 증가
     * @return
     */
    @GetMapping("/api/v3/simple-orders")
    public List<OrderSimpleQueryDto> orderV3() {
        return orderRepository.findAllWithMemberDelivery().stream()
                .map( OrderSimpleQueryDto::new )
                .collect( Collectors.toList() );
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

}
