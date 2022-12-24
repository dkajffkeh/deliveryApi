package com.barogo.domain.order.repository;

import com.barogo.domain.order.Order;
import com.barogo.domain.order.QOrder;
import com.barogo.domain.order.controller.parameter.OrderListRequestParam;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.barogo.domain.auth.QUser.user;
import static com.barogo.domain.order.QOrder.order;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CustomOrderQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Order> findAllByUserIdFetch(Long userSeq, LocalDateTime from, LocalDateTime to) {

        return jpaQueryFactory.selectFrom(order)
                .join(order.user, user).fetchJoin()
                .where(
                        order.user.id.eq(userSeq),
                        order.regDtm.goe(from),
                        order.regDtm.lt(to)
                )
                .fetch();
    }

    public Order findById(Long orderId) {
        return jpaQueryFactory.selectFrom(order)
                .where(order.id.eq(orderId))
                .fetchFirst();
    }
}
