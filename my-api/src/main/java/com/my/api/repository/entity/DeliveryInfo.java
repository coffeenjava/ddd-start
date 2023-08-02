package com.my.api.repository.entity;

import org.springframework.util.Assert;

public class DeliveryInfo {
    private Long deliveryInfoId;
    private String receiverName;
    private String phone;
    private String address;

    public DeliveryInfo(String receiverName, String phone, String address) {
        Assert.hasText(receiverName, "receiver name must not be blank");
        Assert.hasText(phone, "phone must not be blank");
        Assert.hasText(address, "address must not be blank");

        this.receiverName = receiverName;
        this.phone = phone;
        this.address = address;
    }
}
