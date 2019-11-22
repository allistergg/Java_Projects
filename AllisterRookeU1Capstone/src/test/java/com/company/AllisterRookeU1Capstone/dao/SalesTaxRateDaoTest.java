package com.company.AllisterRookeU1Capstone.dao;

import com.company.AllisterRookeU1Capstone.model.SalesTaxRate;
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
public class SalesTaxRateDaoTest {


    @Autowired
    SalesTaxRateDao salesTaxRateDao;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void updateSalesTaxRate() {

        SalesTaxRate gaTax = salesTaxRateDao.findRateByState("GA");
        gaTax.setRate(new BigDecimal(".08"));
        salesTaxRateDao.updateSalesTaxRate(gaTax);
        SalesTaxRate gaTax2 = salesTaxRateDao.findRateByState("GA");
        assertEquals(gaTax, gaTax2);


    }

    @Test
    public void findRateByState() {

        SalesTaxRate tax = new SalesTaxRate();
        tax.setRate(new BigDecimal(".07"));
        tax.setState("GA");

        SalesTaxRate gaTax = salesTaxRateDao.findRateByState("GA");

        assertEquals(tax.getState(), gaTax.getState());
        assertEquals(tax.getRate(), gaTax.getRate());
        assertEquals(tax, gaTax);

        SalesTaxRate prTax = salesTaxRateDao.findRateByState("PR");
        assertNull(prTax);
    }
}