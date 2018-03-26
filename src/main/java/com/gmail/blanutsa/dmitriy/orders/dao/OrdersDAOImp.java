package com.gmail.blanutsa.dmitriy.orders.dao;

import com.gmail.blanutsa.dmitriy.orders.entity.Client;
import com.gmail.blanutsa.dmitriy.orders.entity.Entity;
import com.gmail.blanutsa.dmitriy.orders.entity.Order;
import com.gmail.blanutsa.dmitriy.orders.entity.Product;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAOImp implements OrdersDAO {

    private Connection connection;

    public OrdersDAOImp() {
        ConnectionFactory factory = new ConnectionFactory();
        connection = factory.getConnection();
        createTables();
    }

    private void createTables(){
        try(Statement statement = connection.createStatement()) {
            statement.addBatch("DROP TABLE IF EXISTS orders;");
            statement.addBatch("DROP TABLE IF EXISTS products;");
            statement.addBatch("DROP TABLE IF EXISTS clients;");
            statement.addBatch("CREATE TABLE IF NOT EXISTS products(" +
                    "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                    "title VARCHAR(100) NOT NULL UNIQUE," +
                    "price INT NOT NULL);");
            statement.addBatch("CREATE TABLE IF NOT EXISTS clients(" +
                    "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(100) NOT NULL UNIQUE," +
                    "age INT NOT NULL);");
            statement.addBatch("CREATE TABLE IF NOT EXISTS orders(" +
                    "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                    "productId INT NOT NULL," +
                    "clientId INT NOT NULL," +
                    "amount INT NOT NULL," +
                    "FOREIGN KEY(productId) REFERENCES products(id), " +
                    "FOREIGN KEY(clientId) REFERENCES clients(id));");
            statement.executeBatch();
        } catch (SQLException e) {
            System.out.println("EXCEPTION: CREATE TABLES");
            e.printStackTrace();
        }
    }

    private <T> List<T> getListFromResultSet(ResultSet rs, Class<T> tClass){
        List<T> list = new ArrayList<>();
        try {
            ResultSetMetaData md = rs.getMetaData();
            while (rs.next()) {
                T client = (T) tClass.newInstance();
                for (int i = 1; i <= md.getColumnCount(); i++) {
                    String columnName = md.getColumnName(i);
                    Field field = tClass.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(client, rs.getObject(columnName));
                }
                list.add(client);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return  list;
    }

    @Override
    public List getAllFrom(Entity entity) {
        try(Statement prStm = connection.createStatement()){
            return getListFromResultSet(prStm.executeQuery("SELECT * FROM " + entity.getString()), entity.toClass());
        }catch (SQLException e){
            System.out.println("EXCEPTION: GET ALL " + entity.name());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addProduct(Product product) {
        try(PreparedStatement prStm = connection.prepareStatement("INSERT INTO products VALUES(0, ?, ?);")){
            prStm.setString(1, product.getTitle());
            prStm.setDouble(2, product.getPrice());
            prStm.executeUpdate();
        }catch (SQLException e){
            System.out.println("EXCEPTION: ADD PRODUCT");
            e.printStackTrace();
        }
    }

    @Override
    public void addClient(Client client) {
        try(PreparedStatement prStm = connection.prepareStatement("INSERT INTO clients VALUES(0, ?, ?);")){
            prStm.setString(1, client.getName());
            prStm.setDouble(2, client.getAge());
            prStm.executeUpdate();
        }catch (SQLException e){
            System.out.println("EXCEPTION: ADD CLIENT");
            e.printStackTrace();
        }
    }

    @Override
    public void addOrder(Order order) {
        try(PreparedStatement prStm = connection.prepareStatement("INSERT INTO orders VALUES(0, ?, ?, ?);")){
            prStm.setInt(1, order.getProductId());
            prStm.setInt(2, order.getClientId());
            prStm.setInt(3, order.getAmount());
            prStm.executeUpdate();
        }catch (SQLException e){
            System.out.println("EXCEPTION: ADD ORDER");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProduct(int id){
        try(PreparedStatement prStmOrder = connection.prepareStatement("DELETE FROM orders WHERE productId=?");
            PreparedStatement prStmProduct = connection.prepareStatement("DELETE FROM products WHERE id=?")){
            prStmOrder.setInt(1, id);
            prStmOrder.executeUpdate();
            prStmProduct.setInt(1, id);
            prStmProduct.executeUpdate();
        }catch (SQLException e){
            System.out.println("EXCEPTION: DELETE PRODUCT");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteClient(int id){
        try(PreparedStatement prStmOrder = connection.prepareStatement("DELETE FROM orders WHERE clientId=?");
            PreparedStatement prStmClient = connection.prepareStatement("DELETE FROM clients WHERE id=?")){
            prStmOrder.setInt(1, id);
            prStmOrder.execute();
            prStmClient.setInt(1, id);
            prStmClient.executeUpdate();
        }catch (SQLException e){
            System.out.println("EXCEPTION: DELETE CLIENT");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrder(int id){
        try(PreparedStatement prStm = connection.prepareStatement("DELETE FROM orders WHERE id=?")){
            prStm.setInt(1, id);
            prStm.executeUpdate();
        }catch (SQLException e){
            System.out.println("EXCEPTION: DELETE ORDER");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAllFrom(Entity entity) {
        try(Statement prStm = connection.createStatement()){
            prStm.execute("DELETE FROM " + entity.getString());
        }catch (SQLException e){
            System.out.println("EXCEPTION: DELETE ALL " + entity.name());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        deleteAllFrom(Entity.ORDERS);
        deleteAllFrom(Entity.PRODUCTS);
        deleteAllFrom(Entity.CLIENTS);
    }

}
