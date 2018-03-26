package com.gmail.blanutsa.dmitriy.apartments.dao;

import com.gmail.blanutsa.dmitriy.apartments.entity.Apartment;
import com.gmail.blanutsa.dmitriy.apartments.entity.SortOptions;
import com.gmail.blanutsa.dmitriy.orders.dao.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApartmentDAOImp implements ApartmentDAO{

    private String tableName;

    private Connection connection;

    public ApartmentDAOImp(String tableName) {
        this.tableName = tableName;
        ConnectionFactory factory = new ConnectionFactory();
        connection = factory.getConnection();
        createTable();
    }

    private void createTable(){
        try(Statement statement = connection.createStatement()) {
            statement.addBatch("DROP TABLE IF EXISTS " + tableName + ";");
            statement.addBatch("CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                    "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                    "district VARCHAR(100) NOT NULL," +
                    "address VARCHAR(100) NOT NULL," +
                    "area SMALLINT NOT NULL, " +
                    "roomAmount SMALLINT NOT NULL," +
                    "price SMALLINT NOT NULL);");
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Apartment apartment) {
        try(PreparedStatement prStm = connection.prepareStatement("INSERT INTO " + tableName + " VALUES(0, ?, ?, ?, ?, ?);")){
            prStm.setString(1, apartment.getDistrict());
            prStm.setString(2, apartment.getAddress());
            prStm.setDouble(3, apartment.getArea());
            prStm.setInt(4, apartment.getRoomAmount());
            prStm.setDouble(5, apartment.getPrice());
            prStm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(int id) {
        try(PreparedStatement prStm = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id=?;")){
            prStm.setInt(1, id);
            return prStm.executeUpdate() == 1;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteAll() {
        try(Statement stm = connection.createStatement()){
            return stm.executeUpdate("DELETE FROM " + tableName + ";") == 1;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(int id, Apartment apartment) {
        try(PreparedStatement prStm = connection.prepareStatement("UPDATE " + tableName + " SET district=?, " +
                                                                      "address=?, area=?, roomAmount=?, price=? " +
                                                                      "WHERE id=?;")){
            prStm.setString(1, apartment.getDistrict());
            prStm.setString(2, apartment.getAddress());
            prStm.setDouble(3, apartment.getArea());
            prStm.setInt(4, apartment.getRoomAmount());
            prStm.setDouble(5, apartment.getPrice());
            prStm.setInt(6, id);
            return prStm.executeUpdate() == 1;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    private List<Apartment> getApartmentsFromResultSet(ResultSet resultSet){
        List<Apartment> apartments = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String district = resultSet.getString("district");
                String address = resultSet.getString("address");
                int roomAmount = resultSet.getInt("roomAmount");
                double area = resultSet.getDouble("area");
                double price = resultSet.getDouble("price");
                apartments.add(new Apartment(id, district, address, area, roomAmount, price));
            }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return apartments;
    }

    @Override
    public List<Apartment> getAll() {
        try(Statement stm = connection.createStatement()){
           return getApartmentsFromResultSet(stm.executeQuery("SELECT * FROM " + tableName + ";"));
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Apartment> getAllByOptions(SortOptions sortOptions, String value) {
        try(PreparedStatement prStm = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE " + sortOptions.toString() + "=?;")){
            if (sortOptions == SortOptions.DISTRICT || sortOptions == SortOptions.ADDRESS) {
                prStm.setString(1, value);
            }else {
                prStm.setDouble(1, Double.parseDouble(value));
            }
            return getApartmentsFromResultSet(prStm.executeQuery());
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Apartment> getAllBetween(SortOptions sortOptions, double begin, double end) {
        try(PreparedStatement prStm = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE " + sortOptions.toString() + " BETWEEN ? AND ?;")){
            prStm.setDouble(1, begin);
            prStm.setDouble(2, end);
            return  getApartmentsFromResultSet(prStm.executeQuery());
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

}

