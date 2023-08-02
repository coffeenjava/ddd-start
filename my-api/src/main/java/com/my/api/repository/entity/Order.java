package com.my.api.repository.entity;

import lombok.Getter;
import org.springframework.util.Assert;

import java.util.List;

@Getter
public class Order {
    private Long orderId;

    private DeliveryInfo deliveryInfo;
    private List<OrderDetail> orderDetails;
    private DeliveryState state = DeliveryState.PREPARING;

    public Order(DeliveryInfo deliveryInfo, List<OrderDetail> orderDetails) {
        Assert.notNull(deliveryInfo, "delivery info must not null");
        Assert.notEmpty(orderDetails, "must order at least one");

        this.deliveryInfo = deliveryInfo;
        this.orderDetails = orderDetails;

        orderDetails.forEach(od -> od.setOrderId(orderId));
    }

    public int getTotalAmount() {
        return orderDetails.stream()
                .mapToInt(OrderDetail::getTotalAmount)
                .sum();
    }

    public boolean isDeliveryInfoChangeable() {
        switch (state) {
            case PAYMENT_WAITING:
            case PREPARING:
                return true;
            default:
                return false;
        }
    }

    public void changeDeliveryInfo(DeliveryInfo deliveryInfo) {
        Assert.state(isDeliveryInfoChangeable(), "already shipped");
        this.deliveryInfo = deliveryInfo;
    }

    public void completePayment() {
        if (state == DeliveryState.PAYMENT_WAITING) {
            throw new IllegalStateException("still before payment");
        }

        state = DeliveryState.PREPARING;
    }

    public void completePrepareProduct() {
        Assert.state(state == DeliveryState.PREPARING, "delivery state must be preparing");

        state = DeliveryState.SHIPPED;
    }
}
