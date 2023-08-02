package com.my.api.repository.entity;

import lombok.Getter;
import org.springframework.util.Assert;

import java.util.List;

@Getter
public class Order {
    private Long orderId = 3L;

    private DeliveryInfo deliveryInfo;
    private List<OrderDetail> orderDetails;
    private OrderState state = OrderState.PREPARING;
    private int totalAmount;

    public Order(DeliveryInfo deliveryInfo, List<OrderDetail> orderDetails) {
        Assert.notNull(deliveryInfo, "delivery info must not null");
        Assert.notEmpty(orderDetails, "must order at least one");

        this.deliveryInfo = deliveryInfo;
        this.orderDetails = orderDetails;

        orderDetails.forEach(od -> {
            od.setOrderId(orderId);
            this.totalAmount += od.getTotalAmount();
        });
    }

    public void completePayment() {
        if (state == OrderState.PAYMENT_WAITING) {
            throw new IllegalStateException("still before payment");
        }

        state = OrderState.PREPARING;
    }

    public void completePrepareProduct() {
        Assert.state(state == OrderState.PREPARING, "delivery state must be preparing");

        state = OrderState.SHIPPED;
    }

    public void changeDeliveryInfo(DeliveryInfo deliveryInfo) {
        verifyNotYetShipped();
        this.deliveryInfo = deliveryInfo;
    }

    public void cancel() {
        verifyNotYetShipped();
        state = OrderState.CANCELED;
    }

    private void verifyNotYetShipped() {
        switch (state) {
            case PAYMENT_WAITING:
            case PREPARING:
                return;
        }

        throw new IllegalStateException("already shipped");
    }
}
