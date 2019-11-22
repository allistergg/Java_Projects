package com.company.AllisterRookeU1Capstone.dao;

import com.company.AllisterRookeU1Capstone.model.TShirt;

import java.util.List;

public interface TShirtDao {

    TShirt findTShirt(int id);

    List<TShirt> findAllTShirts();

    TShirt addTShirt(TShirt tShirt);

    void updateTShirt(TShirt tShirt);

    void deleteTShirt(int id);

    List<TShirt> findTShirtBySize(String size);

    List<TShirt> findTShirtByColor(String color);
}
