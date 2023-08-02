package com.my.api.repository.entity;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class DeliveryInfo {
    private Long deliveryInfoId;
    private final String receiverName;
    private final String phone;
    private final String address;

    public DeliveryInfo(String receiverName, String phone, String address) {
        Assert.hasText(receiverName, "receiver name must not be blank");
        Assert.hasText(phone, "phone must not be blank");
        Assert.hasText(address, "address must not be blank");

        this.receiverName = receiverName;
        this.phone = phone;
        this.address = address;
    }
}
