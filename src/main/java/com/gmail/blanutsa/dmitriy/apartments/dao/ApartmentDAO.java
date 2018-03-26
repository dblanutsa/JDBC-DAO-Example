package com.gmail.blanutsa.dmitriy.apartments.dao;

import com.gmail.blanutsa.dmitriy.apartments.entity.Apartment;
import com.gmail.blanutsa.dmitriy.apartments.entity.SortOptions;

import java.util.List;

public interface ApartmentDAO {

    void add(Apartment apartment);

    boolean delete(int id);

    boolean deleteAll();

    boolean update(int id, Apartment apartment);

    List<Apartment> getAll();

    List<Apartment> getAllByOptions(SortOptions sortOptions, String address);

    List<Apartment> getAllBetween(SortOptions sortOptions, double begin, double end);
}
