package com.barogo.domain.order.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderSearchHistoryPayload {

    private List<OrderDetailPayload> orders = new ArrayList<>();

}
