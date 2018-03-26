package com.gmail.blanutsa.dmitriy.orders.entity;

import java.util.Objects;

public class Order {

    private int id;
    private int productId;
    private int clientId;
    private int amount;

    public Order() {
    }

    public Order(int id, int productId, int clientId, int amount) {
        this.id = id;
        this.productId = productId;
        this.clientId = clientId;
        this.amount = amount;
    }

    public Order(int productId, int clientId, int amount) {
        this.productId = productId;
        this.clientId = clientId;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return productId == order.productId &&
                clientId == order.clientId &&
                amount == order.amount;
    }

    @Override
    public int hashCode() {

        return Objects.hash(productId, clientId, amount);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", productId=" + productId +
                ", clientId=" + clientId +
                ", amount=" + amount +
                '}';
    }
}
