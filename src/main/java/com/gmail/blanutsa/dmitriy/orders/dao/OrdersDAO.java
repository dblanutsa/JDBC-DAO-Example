package com.gmail.blanutsa.dmitriy.orders.dao;

import com.gmail.blanutsa.dmitriy.orders.entity.Client;
import com.gmail.blanutsa.dmitriy.orders.entity.Entity;
import com.gmail.blanutsa.dmitriy.orders.entity.Order;
import com.gmail.blanutsa.dmitriy.orders.entity.Product;

import java.util.List;

public interface OrdersDAO {

    public List<Product> getAllFrom(Entity entity);

    public void addProduct(Product product);

    public void addClient(Client client);

    public void addOrder(Order order);

    public void deleteProduct(int id);

    public void deleteClient(int id);

    public void deleteOrder(int id);

    public void deleteAllFrom(Entity entity);

    public void deleteAll();

}
