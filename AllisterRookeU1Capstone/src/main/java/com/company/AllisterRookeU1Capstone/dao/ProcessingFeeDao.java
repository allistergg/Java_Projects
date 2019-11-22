package com.company.AllisterRookeU1Capstone.dao;

import com.company.AllisterRookeU1Capstone.model.ProcessingFee;

import java.math.BigDecimal;

public interface ProcessingFeeDao {

    void updateProcessingFee(ProcessingFee processingFee);

    ProcessingFee findFeeByProductType(String productType);
}
