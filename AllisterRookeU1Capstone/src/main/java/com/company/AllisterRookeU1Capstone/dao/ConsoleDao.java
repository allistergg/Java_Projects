package com.company.AllisterRookeU1Capstone.dao;

import com.company.AllisterRookeU1Capstone.model.Console;

import java.util.List;
import java.util.Optional;

public interface ConsoleDao {

    Console findConsole(int id);

    List<Console> findAllConsoles();

    Console addConsole(Console console);

    void updateConsole(Console console);

    void deleteConsole(int id);

    List<Console> findConsoleByManufacturer(String manufacturer);
}
