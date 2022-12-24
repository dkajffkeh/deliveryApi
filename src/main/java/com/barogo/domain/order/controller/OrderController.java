package com.barogo.domain.order.controller;

import com.barogo.common.payload.response.ResponseData;
import com.barogo.domain.order.application.OrderService;
import com.barogo.domain.order.controller.parameter.AddressModifyRequestParam;
import com.barogo.domain.order.controller.parameter.OrderListRequestParam;
import com.barogo.domain.order.controller.payload.OrderDetailPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseData<List<OrderDetailPayload>> getOrders(
            @RequestBody
            @Validated OrderListRequestParam orderListRequestParam) {
        return ResponseData.success(orderService.getOrderList(orderListRequestParam));
    }

    @PatchMapping("/{orderId}/address")
    public ResponseData<OrderDetailPayload> modifyOrderAddress(
            @PathVariable Long orderId,
            @RequestBody
            @Validated AddressModifyRequestParam addressModifyRequestParam
            ) {
        return ResponseData.success(orderService.modifyOrderAddress(orderId,addressModifyRequestParam));
    }
}
