package com.company.AllisterRookeU1Capstone.dao;

import com.company.AllisterRookeU1Capstone.model.TShirt;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TShirtJdbcTemplateImpl implements TShirtDao{

    //    t_shirt_id int(11) not null auto_increment primary key,
//    size varchar(20) not null,
//    color varchar(20) not null,
//    description varchar(255) not null,
//    price decimal(5,2) not null,
//    quantity int(11) not null

    private final String FIND_TSHIRT_SQL =
            "SELECT * FROM t_shirt WHERE t_shirt_id = ?";
    private final String FIND_ALL_TSHIRTS_SQL =
            "SELECT * FROM t_shirt";
    private final String ADD_TSHIRT_SQL =
            "INSERT INTO t_shirt (size, color, description, price, quantity) VALUES (?,?,?,?,?)";
    private final String UPDATE_TSHIRT_SQL =
            "UPDATE t_shirt SET size = ?, color = ?, description = ?, price = ?, quantity = ? WHERE t_shirt_id = ?";
    private final String DELETE_TSHIRT_SQL =
            "DELETE FROM t_shirt WHERE t_shirt_id = ?";
    private final String FIND_TSHIRT_BY_COLOR_SQL =
            "SELECT * FROM t_shirt WHERE color = ?";
    private final String FIND_TSHIRT_BY_SIZE_SQL =
            "SELECT * FROM t_shirt WHERE size = ?";

    JdbcTemplate jdbcTemplate;

    public TShirtJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TShirt findTShirt(int id) {
        try {
            return jdbcTemplate.queryForObject(FIND_TSHIRT_SQL, new BeanPropertyRowMapper<>(TShirt.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<TShirt> findAllTShirts() {
        return jdbcTemplate.query(FIND_ALL_TSHIRTS_SQL, new BeanPropertyRowMapper<>(TShirt.class));
    }

    @Override
    public TShirt addTShirt(TShirt tShirt) {
        jdbcTemplate.update(ADD_TSHIRT_SQL,
                tShirt.getSize(),
                tShirt.getColor(),
                tShirt.getDescription(),
                tShirt.getPrice(),
                tShirt.getQuantity());

        int id = jdbcTemplate.queryForObject("SELECT last_insert_id()", Integer.class);

        tShirt.setTShirtId(id);
        return tShirt;
    }

    @Override
    public void updateTShirt(TShirt tShirt) {

        jdbcTemplate.update(UPDATE_TSHIRT_SQL,
                tShirt.getSize(),
                tShirt.getColor(),
                tShirt.getDescription(),
                tShirt.getPrice(),
                tShirt.getQuantity(),
                tShirt.getTShirtId());

    }

    @Override
    public void deleteTShirt(int id) {
        jdbcTemplate.update(DELETE_TSHIRT_SQL, id);
    }

    @Override
    public List<TShirt> findTShirtBySize(String size) {
        return jdbcTemplate.query(FIND_TSHIRT_BY_SIZE_SQL, new BeanPropertyRowMapper<>(TShirt.class), size);
    }

    @Override
    public List<TShirt> findTShirtByColor(String color) {
        return jdbcTemplate.query(FIND_TSHIRT_BY_COLOR_SQL, new BeanPropertyRowMapper<>(TShirt.class), color);
    }
}
