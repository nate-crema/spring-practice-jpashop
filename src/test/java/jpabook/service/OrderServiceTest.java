package jpabook.service;

import jakarta.persistence.EntityManager;
import jpabook.domain.*;
import jpabook.domain.item.Book;
import jpabook.domain.item.Item;
import jpabook.exception.NotEnoughStockException;
import jpabook.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // given
        Member member = new Member();
        member.setName("sef");
        member.setAddress(new Address("서울", "기흥", "00001"));
        em.persist( member );

        Item book = new Book();
        book.setName("sddd");
        book.setPrice(5000);
        book.setStockQuantity(10);
        em.persist( book );

        // when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(5000 * orderCount, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다.");
        assertEquals(8, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        // given
        Member member = new Member();
        member.setName("sejf");
        member.setAddress(new Address( "sef", "sef", "42789" ));

        Item book = new Book();
        book.setName("TEST_BOOK");
        book.setPrice(13000);
        book.setStockQuantity(20);

        // when
        em.persist( member );
        em.persist( book );

        // then
        assertThrows( NotEnoughStockException.class, () -> {
            Long orderId = orderService.order(member.getId(), book.getId(), 30);
        } );
    }

    @Test
    public void 주문취소() throws Exception {
        // given
        Member member = new Member();
        member.setName("sejf");
        member.setAddress(new Address( "sef", "sef", "42789" ));

        Item book = new Book();
        book.setName("TEST_BOOK");
        book.setPrice(13000);
        book.setStockQuantity(20);

        em.persist( member );
        em.persist( book );
        Long orderId = orderService.order(member.getId(), book.getId(), 10);

        // when
        orderService.cancelOrder( orderId );

        // then
        Order getOrder = orderRepository.findOne( orderId );
        assertEquals( OrderStatus.CANCEL, getOrder.getStatus() );
        assertEquals( 20, book.getStockQuantity() );
    }
}