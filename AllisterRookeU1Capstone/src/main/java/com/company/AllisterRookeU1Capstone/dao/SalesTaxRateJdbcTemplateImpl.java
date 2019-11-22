package com.company.AllisterRookeU1Capstone.dao;

import com.company.AllisterRookeU1Capstone.model.SalesTaxRate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SalesTaxRateJdbcTemplateImpl implements SalesTaxRateDao {

    private final String FIND_SALES_TAX_RATE_SQL =
            "SELECT * FROM sales_tax_rate WHERE state = ?";
    private final String UPDATE_SALES_TAX_SQL =
            "UPDATE sales_tax_rate SET rate = ? WHERE state = ?";

    JdbcTemplate jdbcTemplate;

    public SalesTaxRateJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void updateSalesTaxRate(SalesTaxRate salesTaxRate) {
        jdbcTemplate.update(UPDATE_SALES_TAX_SQL,
                salesTaxRate.getRate(),
                salesTaxRate.getState());
    }

    @Override
    public SalesTaxRate findRateByState(String state) {
        try {
            return jdbcTemplate.queryForObject(FIND_SALES_TAX_RATE_SQL, new BeanPropertyRowMapper<>(SalesTaxRate.class), state);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
