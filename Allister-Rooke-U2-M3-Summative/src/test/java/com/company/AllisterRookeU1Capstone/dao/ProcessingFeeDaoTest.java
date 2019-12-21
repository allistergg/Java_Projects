package com.company.AllisterRookeU1Capstone.dao;

import com.company.AllisterRookeU1Capstone.model.ProcessingFee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProcessingFeeDaoTest {

    @Autowired
    ProcessingFeeDao processingFeeDao;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void updateProcessingFee() {

        ProcessingFee pFee = processingFeeDao.findFeeByProductType("Games");
        pFee.setFee(new BigDecimal("0.01"));
        processingFeeDao.updateProcessingFee(pFee);
        ProcessingFee pFee2 = processingFeeDao.findFeeByProductType("Games");
        assertEquals(pFee, pFee2);
    }

    @Test
    public void findFeeByProductType() {


        ProcessingFee proFee = new ProcessingFee();
        proFee.setFee(new BigDecimal("14.99"));
        proFee.setProductType("Consoles");

        ProcessingFee pFee = processingFeeDao.findFeeByProductType("Consoles");
        assertEquals(proFee.getFee(), pFee.getFee());
        assertEquals(proFee.getProductType(), pFee.getProductType());
        assertEquals(proFee, pFee);

        pFee = processingFeeDao.findFeeByProductType("Garbage");
        assertNull(pFee);



    }
}