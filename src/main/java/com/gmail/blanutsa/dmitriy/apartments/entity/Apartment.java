package com.gmail.blanutsa.dmitriy.apartments.entity;

import java.util.Objects;

public class Apartment {

    private int id;
    private String district;
    private String address;
    private double area;
    private int roomAmount;
    private double price;

    public Apartment(int id, String district, String address, double area, int roomAmount, double price) {
        this.id = id;
        this.district = district;
        this.address = address;
        this.area = area;
        this.roomAmount = roomAmount;
        this.price = price;
    }

    public Apartment(String district, String address, double area, int roomAmount, double price) {
        this.district = district;
        this.address = address;
        this.area = area;
        this.roomAmount = roomAmount;
        this.price = price;
    }

    public Apartment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getRoomAmount() {
        return roomAmount;
    }

    public void setRoomAmount(int roomAmount) {
        this.roomAmount = roomAmount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return  Double.compare(apartment.area, area) == 0 &&
                roomAmount == apartment.roomAmount &&
                Double.compare(apartment.price, price) == 0 &&
                Objects.equals(district, apartment.district) &&
                Objects.equals(address, apartment.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(district, address, area, roomAmount, price);
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", district='" + district + '\'' +
                ", address='" + address + '\'' +
                ", area=" + area +
                ", roomAmount=" + roomAmount +
                ", price=" + price +
                '}';
    }
}
