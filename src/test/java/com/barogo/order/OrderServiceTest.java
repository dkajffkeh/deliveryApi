package com.barogo.order;

import com.barogo.common.exception.ApiServerException;
import com.barogo.domain.order.Order;
import com.barogo.domain.order.controller.parameter.AddressModifyRequestParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.barogo.common.type.OrderStatus.DELIVERING;
import static com.barogo.common.type.OrderStatus.ORDER_RECEIVING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderServiceTest {

    private static final AddressModifyRequestParam TEST_PARAM =
            new AddressModifyRequestParam(
                    "ParamAddressTitle",
                    "ParamAddress"
            );

    @Test
    @DisplayName("주문 변경 가능상태 변경 - 정상 변경케이스")
    void modifySuccessCase() {

        Order order = new Order(
                1L,
                null,
                "testMenu",
                "testStore",
                "testAddress",
                "testTitle",
                ORDER_RECEIVING
        );

        order.modifyAddressInfo(TEST_PARAM);
        assertEquals(order.getAddress(),TEST_PARAM.getAddress());
    }

    @Test
    @DisplayName("주문 변경 불가상태 변경 - 에러 케이스")
    void modifyErrorCase() {

        Order order = new Order(
                1L,
                null,
                "testMenu",
                "testStore",
                "testAddress",
                "testTitle",
                DELIVERING
        );

        assertThrows(ApiServerException.class,() -> order.modifyAddressInfo(TEST_PARAM));

    }
}