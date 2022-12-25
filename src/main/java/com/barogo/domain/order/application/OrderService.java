package com.barogo.domain.order.application;

import com.barogo.common.code.ResultCode;
import com.barogo.common.exception.ApiServerException;
import com.barogo.domain.order.Order;
import com.barogo.domain.order.controller.parameter.AddressModifyRequestParam;
import com.barogo.domain.order.controller.parameter.OrderListRequestParam;
import com.barogo.domain.order.controller.payload.OrderDetailPayload;
import com.barogo.domain.order.controller.payload.OrderSearchHistoryPayload;
import com.barogo.domain.order.repository.CustomOrderQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.barogo.domain.order.Order.ORDER_NOT_FOUND_MSG;
import static com.barogo.domain.order.controller.payload.OrderDetailPayload.buildDetailPayloadsByOrder;
import static org.springframework.util.ObjectUtils.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final CustomOrderQueryRepository customOrderQueryRepository;

    public OrderSearchHistoryPayload getOrderList(OrderListRequestParam orderListRequestParam) {
        orderListRequestParam.validateRequestedDate();

        List<Order> orders = customOrderQueryRepository.findAllByUserIdFetch(
                orderListRequestParam.getUserSeq(),
                orderListRequestParam.convertedFromTimeGet(),
                orderListRequestParam.convertedToTimeGet()
                );

        return new OrderSearchHistoryPayload(buildDetailPayloadsByOrder(orders));
    }

    @Transactional
    public OrderDetailPayload modifyOrderAddress(Long orderId, AddressModifyRequestParam addressModifyRequestParam) {
        Order order = customOrderQueryRepository.findById(orderId);
        if(isEmpty(order)) throw new ApiServerException(ResultCode.RESULT_4000, ORDER_NOT_FOUND_MSG);
        order.modifyAddressInfo(addressModifyRequestParam);
        return new OrderDetailPayload(order);
    }

}
