package com.barogo.domain.order.controller.payload;

import com.barogo.common.type.OrderStatus;
import com.barogo.domain.auth.User;
import com.barogo.domain.order.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.barogo.common.utils.DateTimeUtils.convertLocalDateTimeToString;
import static org.springframework.util.ObjectUtils.isEmpty;

@Getter
@AllArgsConstructor
public class OrderDetailPayload {

    private String address;

    private String addressTitle;

    private String username;

    private String menu;

    private String storeName;

    private String orderDateTime;

    private OrderStatus orderStatus;

    public OrderDetailPayload(Order order) {
        final User user = order.getUser();

        this.address = order.getAddress();
        this.addressTitle = order.getAddressTitle();
        this.username = user.getUsername();
        this.menu = order.getMenu();
        this.storeName = order.getStoreName();
        this.orderDateTime = convertLocalDateTimeToString(order.getRegDtm());
        this.orderStatus = order.getOrderStatus();
    }

    public static List<OrderDetailPayload> buildDetailPayloadsByOrder(List<Order> orders) {
        if(isEmpty(orders)) return new ArrayList<>();
        List<OrderDetailPayload> payloads = new ArrayList<>();
        orders.forEach(order -> payloads.add(new OrderDetailPayload(order)));
        return payloads;
    }
}
