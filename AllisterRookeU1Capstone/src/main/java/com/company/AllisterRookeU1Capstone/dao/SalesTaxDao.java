package com.company.AllisterRookeU1Capstone.dao;

import com.company.AllisterRookeU1Capstone.model.SalesTaxRate;

import java.math.BigDecimal;

public interface SalesTaxDao {

    SalesTaxRate findRateByState(String state);
}
