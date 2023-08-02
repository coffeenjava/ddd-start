package com.my.api.repository.entity;

import lombok.Getter;

@Getter
public class Product {
    private Long productId;
    private int amount;

    public Product(int amount) {
        this.amount = amount;
    }
}
