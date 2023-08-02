package com.my.api.repository.entity;

import lombok.Getter;

@Getter
public class OrderDetail {
    private Long orderDetailId;

    private Long orderId;
    private Product product;
    private int orderCount;
    private int totalAmount;

    public OrderDetail(Product product, int orderCount) {
        this.product = product;
        this.orderCount = orderCount;
        this.totalAmount = product.getAmount() * orderCount;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
