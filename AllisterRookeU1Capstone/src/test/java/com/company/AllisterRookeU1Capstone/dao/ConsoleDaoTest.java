package com.company.AllisterRookeU1Capstone.dao;

import com.company.AllisterRookeU1Capstone.model.Console;
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
public class ConsoleDaoTest {

    @Autowired
    private ConsoleDao consoleDao;

    private Console console;

    @Before
    public void setUp() throws Exception {

        List<Console> consoles = consoleDao.findAllConsoles();

        for (Console console : consoles) {
            consoleDao.deleteConsole(console.getConsoleId());
        }

        console = new Console("XBox", "Microsoft", "8GB", "Intel", new BigDecimal("199.99"), 10);
    }

    @Test
    public void addFindConsole() {
        console = consoleDao.addConsole(console);

        Console console2 = consoleDao.findConsole(console.getConsoleId());
        assertEquals(console, console2);

    }

    @Test
    public void findAllConsoles() {

        consoleDao.addConsole(console);
        List<Console> consoles = consoleDao.findAllConsoles();
        assertEquals(1, consoles.size());
    }

    @Test
    public void updateConsole() {

        console = consoleDao.addConsole(console);
        console.setQuantity(console.getQuantity()-1);
        consoleDao.updateConsole(console);
        Console console2 = consoleDao.findConsole(console.getConsoleId());
        assertEquals(console, console2);
    }

    @Test
    public void deleteConsole() {

        console = consoleDao.addConsole(console);
        consoleDao.deleteConsole(console.getConsoleId());
        console = consoleDao.findConsole(console.getConsoleId());
        assertNull(console);

    }

    @Test
    public void findConsoleByManufacturer() {

        console = consoleDao.addConsole(console);
        List<Console> consoles = consoleDao.findConsoleByManufacturer("Microsoft");
        assertEquals(1, consoles.size());
        consoles = consoleDao.findConsoleByManufacturer("Sony");
        assertEquals(0, consoles.size());
    }
}