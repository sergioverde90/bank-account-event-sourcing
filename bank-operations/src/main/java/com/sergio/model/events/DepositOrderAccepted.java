package com.sergio.model.events;

import com.sergio.model.OrderInfo;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class DepositOrderAccepted extends Order {

    @JsonbCreator
    public DepositOrderAccepted(@JsonbProperty("id") String orderAccepted) {
        super(orderAccepted);
    }

    @Override
    public void apply(OrderInfo order) {
        order.setStatus(OrderInfo.OrderStatusType.ACCEPTED);
    }

}
