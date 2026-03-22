package com.example.orderservice.dto;

public class OrderResponse {

    private Long orderId;
    private Object product;

    public OrderResponse(Long orderId, Object product) {
        this.orderId = orderId;
        this.product = product;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Object getProduct() {
        return product;
    }
}