package com.gmail.blanutsa.dmitriy.apartments.entity;

public enum SortOptions {
    DISTRICT("district"), ADDRESS("address"), PRICE("price"), AREA("area"), ROOMS_AMOUNT("roomAmount");

    private String value;


    SortOptions(String value){
        this.value = value;
    }

    @Override
    public String toString(){
        return value;
    }
}
