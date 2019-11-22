package com.company.AllisterRookeU1Capstone.dao;

import com.company.AllisterRookeU1Capstone.model.ProcessingFee;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProcessingFeeJdbcTemplateImpl implements ProcessingFeeDao {

    private final String UPDATE_PROCESSING_FEE_SQL =
            "UPDATE processing_fee SET fee = ? WHERE product_type = ?";
    private final String FIND_PROCEESSING_FEE_SQL =
            "SELECT * FROM processing_fee WHERE product_type = ?";

    JdbcTemplate jdbcTemplate;

    public ProcessingFeeJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void updateProcessingFee(ProcessingFee processingFee) {
        jdbcTemplate.update(UPDATE_PROCESSING_FEE_SQL,
                processingFee.getFee(),
                processingFee.getProductType());

    }

    @Override
    public ProcessingFee findFeeByProductType(String productType) {
        try {
            return jdbcTemplate.queryForObject(FIND_PROCEESSING_FEE_SQL, new BeanPropertyRowMapper<>(ProcessingFee.class), productType);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}