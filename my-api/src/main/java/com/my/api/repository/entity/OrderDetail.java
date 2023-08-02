package com.my.api.repository.entity;

public class OrderDetail {
    private Long orderDetailId;

    private Long orderId;
    private Long productId;
    private int productCount;

    public OrderDetail(Long productId, int productCount) {
        this.productId = productId;
        this.productCount = productCount;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public int getTotalAmount() {
        return 100 * productCount;
    }
}
