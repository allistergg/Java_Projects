package com.company.AllisterRookeU1Capstone.dao;

import com.company.AllisterRookeU1Capstone.model.SalesTaxRate;

import java.math.BigDecimal;
import java.util.List;

public interface SalesTaxRateDao {


    void updateSalesTaxRate(SalesTaxRate salesTaxRate);

    SalesTaxRate findRateByState(String state);
}
