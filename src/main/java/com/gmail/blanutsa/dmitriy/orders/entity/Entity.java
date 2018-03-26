package com.gmail.blanutsa.dmitriy.orders.entity;

public enum Entity {
    PRODUCTS("products", Product.class), CLIENTS("clients", Client.class), ORDERS("orders", Order.class);

    private String str;
    private Class tClass;

    Entity(String str, Class tClass){
        this.str = str;
        this.tClass = tClass;
    }

    public String getString() {
        return str;
    }

    public Class toClass() {
        return tClass;
    }
}
