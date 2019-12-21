package com.company.AllisterRookeU1Capstone.dao;

import com.company.AllisterRookeU1Capstone.model.Console;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ConsoleDaoJdbcTemplateImpl implements ConsoleDao {

    //    console_id int(11) not null auto_increment primary key,
//    model varchar(50) not null,
//    manufacturer varchar(50) not null,
//    memory_amount varchar(20),
//    processor varchar(20),
//    price decimal(5, 2) not null,
//    quantity int(11) not null

    private final String FIND_CONSOLE_SQL =
            "SELECT * FROM console WHERE console_id = ?";
    private final String FIND_ALL_CONSOLES_SQL =
            "SELECT * FROM console";
    private final String ADD_CONSOLE_SQL =
            "INSERT INTO console (model, manufacturer, memory_amount, processor, price, quantity) VALUES (?,?,?,?,?,?)";
    private final String UPDATE_CONSOLE_SQL =
            "UPDATE console SET model = ?, manufacturer = ?, memory_amount = ?, processor = ?, price = ?, quantity = ? WHERE console_id = ?";
    private final String DELETE_CONSOLE_SQL =
            "DELETE FROM console WHERE console_id = ?";
    private final String FIND_CONSOLE_BY_MANUFACTURER_SQL =
            "SELECT * FROM console WHERE manufacturer = ?";

    JdbcTemplate jdbcTemplate;

    public ConsoleDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Console findConsole(int id) {
        try {
            return jdbcTemplate.queryForObject(FIND_CONSOLE_SQL, new BeanPropertyRowMapper<>(Console.class), id);
        } catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public List<Console> findAllConsoles() {
        return jdbcTemplate.query(FIND_ALL_CONSOLES_SQL, new BeanPropertyRowMapper<>(Console.class));
    }

    @Override
    public Console addConsole(Console console) {
        jdbcTemplate.update(ADD_CONSOLE_SQL,
                console.getModel(),
                console.getManufacturer(),
                console.getMemoryAmount(),
                console.getProcessor(),
                console.getPrice(),
                console.getQuantity());

        int id = jdbcTemplate.queryForObject("SELECT last_insert_id()", Integer.class );

        console.setConsoleId(id);
        return console;
    }

    @Override
    public void updateConsole(Console console) {

        jdbcTemplate.update(UPDATE_CONSOLE_SQL,
                console.getModel(),
                console.getManufacturer(),
                console.getMemoryAmount(),
                console.getProcessor(),
                console.getPrice(),
                console.getQuantity(),
                console.getConsoleId());

    }

    @Override
    public void deleteConsole(int id) {

        jdbcTemplate.update(DELETE_CONSOLE_SQL, id);

    }

    @Override
    public List<Console> findConsoleByManufacturer(String manufacturer) {
        return jdbcTemplate.query(FIND_CONSOLE_BY_MANUFACTURER_SQL, new BeanPropertyRowMapper<>(Console.class), manufacturer);
    }
}