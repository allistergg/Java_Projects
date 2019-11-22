package com.company.AllisterRookeU1Capstone.dao;

import com.company.AllisterRookeU1Capstone.model.TShirt;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TShirtDaoTest {

    @Autowired
    TShirtDao tShirtDao;

    TShirt tShirt;

    @Before
    public void setUp() throws Exception {

        List<TShirt> tShirts = tShirtDao.findAllTShirts();

        for (TShirt tShirt : tShirts) {
            tShirtDao.deleteTShirt(tShirt.getTShirtId());
        }

        tShirt = new TShirt("M", "green", "blank", new BigDecimal("9.99"), 10);
    }

    @Test
    public void findAddTShirt() {

        tShirt = tShirtDao.addTShirt(tShirt);
        TShirt tShirt2 = tShirtDao.findTShirt(tShirt.getTShirtId());
        assertEquals(tShirt, tShirt2);
    }

    @Test
    public void findAllTShirts() {
        tShirtDao.addTShirt(tShirt);
        List<TShirt> tShirts = tShirtDao.findAllTShirts();
        assertEquals(1, tShirts.size());
    }

    @Test
    public void updateTShirt() {

        tShirt = tShirtDao.addTShirt(tShirt);
        tShirt.setQuantity(tShirt.getQuantity()-1);
        tShirtDao.updateTShirt(tShirt);
        TShirt tShirt2 = tShirtDao.findTShirt(tShirt.getTShirtId());
        assertEquals(tShirt, tShirt2);
    }

    @Test
    public void deleteTShirt() {

        tShirt =tShirtDao.addTShirt(tShirt);
        tShirtDao.deleteTShirt(tShirt.getTShirtId());
        TShirt tShirt2 = tShirtDao.findTShirt(tShirt.getTShirtId());
        assertNull(tShirt2);
    }

    @Test
    public void findTShirtBySize() {

        tShirt = tShirtDao.addTShirt(tShirt);
        List<TShirt> tShirts = tShirtDao.findTShirtBySize("S");
        assertEquals(0, tShirts.size());
        tShirts = tShirtDao.findTShirtBySize("M");
        assertEquals(1, tShirts.size());
    }

    @Test
    public void findTShirtByColor() {

        tShirt = tShirtDao.addTShirt(tShirt);
        List<TShirt> tShirts = tShirtDao.findTShirtByColor("green");
        assertEquals(1, tShirts.size());
        tShirts = tShirtDao.findTShirtByColor("yellow");
        assertEquals(0, tShirts.size());
    }
}