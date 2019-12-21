package com.company.AllisterRookeU1Capstone.dao;

import com.company.AllisterRookeU1Capstone.model.Game;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameDaoJdbcTemplateImpl implements GameDao {

    //    game_id int(11) not null auto_increment primary key,
//    title varchar(50) not null,
//    esrb_rating varchar(50) not null,
//    description varchar(255) not null,
//    price decimal(5, 2) not null,
//    studio varchar(50) not null,
//    quantity int(11)

    public final String FIND_GAME_SQL =
            "SELECT * FROM game WHERE game_id = ?";
    public final String FIND_ALL_GAMES_SQL =
            "SELECT * FROM game";
    public final String ADD_GAME_SQL =
            "INSERT INTO game (title, esrb_rating, description, price, studio, quantity) VALUES (?,?,?,?,?,?)";
    public final String UPDATE_GAME_SQL =
            "UPDATE game SET title = ?, esrb_rating = ?, description = ?, price = ?, studio = ?, quantity = ? WHERE game_id = ?";
    public final String DELETE_GAME_SQL =
            "DELETE FROM game WHERE game_id = ?";
    public final String FIND_GAME_BY_TITLE_SQL =
            "SELECT * FROM game WHERE title = ?";
    public final String FIND_GAME_BY_STUDIO_SQL =
            "SELECT * FROM game WHERE studio = ?";
    public final String FIND_GAME_BY_ESRB_SQL =
            "SELECT * FROM game WHERE esrb_rating = ?";

    JdbcTemplate jdbcTemplate;

    public GameDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Game findGame(int id) {
        try {
            return jdbcTemplate.queryForObject(FIND_GAME_SQL, new BeanPropertyRowMapper<>(Game.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Game> findAllGames() {
        return jdbcTemplate.query(FIND_ALL_GAMES_SQL, new BeanPropertyRowMapper<>(Game.class));
    }

    @Override
    public Game addGame(Game game) {
        jdbcTemplate.update(ADD_GAME_SQL,
                game.getTitle(),
                game.getEsrbRating(),
                game.getDescription(),
                game.getPrice(),
                game.getStudio(),
                game.getQuantity());

        int id = jdbcTemplate.queryForObject("SELECT last_insert_id()", Integer.class);

        game.setGameId(id);

        return game;
    }

    @Override
    public void updateGame(Game game) {
        jdbcTemplate.update(UPDATE_GAME_SQL,
                game.getTitle(),
                game.getEsrbRating(),
                game.getDescription(),
                game.getPrice(),
                game.getStudio(),
                game.getQuantity(),
                game.getGameId());

    }

    @Override
    public void deleteGame(int id) {
        jdbcTemplate.update(DELETE_GAME_SQL, id);
    }

    @Override
    public List<Game> findGamesByStudio(String studio) {
        return jdbcTemplate.query(FIND_GAME_BY_STUDIO_SQL, new BeanPropertyRowMapper<>(Game.class), studio);
    }

    @Override
    public List<Game> findGamesByEsrbRating(String esrbRating) {
        return jdbcTemplate.query(FIND_GAME_BY_ESRB_SQL, new BeanPropertyRowMapper<>(Game.class), esrbRating);
    }

    @Override
    public List<Game> findGamesByTitle(String title) {
        return jdbcTemplate.query(FIND_GAME_BY_TITLE_SQL, new BeanPropertyRowMapper<>(Game.class), title);
    }
}
