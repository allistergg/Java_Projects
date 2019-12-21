package com.company.AllisterRookeU1Capstone.dao;

import com.company.AllisterRookeU1Capstone.model.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GameDaoTest {

    @Autowired
    GameDao gameDao;

    private Game game;

    @Before
    public void setUp() throws Exception {
        List<Game> games = gameDao.findAllGames();

        for (Game game : games) {
            gameDao.deleteGame(game.getGameId());
        }

        game = new Game("Fifa 2020", "AA", "soccer", new BigDecimal("9.99"), "EA Sports", 4);

    }

    @Test
    public void addFindGame() {

        game = gameDao.addGame(game);

        Game game2 = gameDao.findGame(game.getGameId());

        assertEquals(game, game2);

    }

    @Test
    public void findAllGames() {
        gameDao.addGame(game);
        List<Game> games = gameDao.findAllGames();
        assertEquals(1, games.size());
    }

    @Test
    public void updateGame() {
        game = gameDao.addGame(game);
        game.setQuantity(game.getQuantity()-1);
        gameDao.updateGame(game);
        Game game2 = gameDao.findGame(game.getGameId());
        assertEquals(game, game2);
    }

    @Test
    public void deleteGame() {

        game = gameDao.addGame(game);
        gameDao.deleteGame(game.getGameId());
        Game game2 = gameDao.findGame(game.getGameId());
        assertNull(game2);
    }

    @Test
    public void findGamesByStudio() {

        gameDao.addGame(game);
        List<Game> games = gameDao.findGamesByStudio("EA Sports");
        assertEquals(1, games.size());
        games = gameDao.findGamesByStudio("Konami");
        assertEquals(0, games.size());
    }

    @Test
    public void findGamesByEsrb() {
        gameDao.addGame(game);
        List<Game> games = gameDao.findGamesByEsrbRating("AA");
        assertEquals(1, games.size());
        games = gameDao.findGamesByEsrbRating("NC-17");
        assertEquals(0, games.size());
    }

    @Test
    public void findGamesByTitle() {

        gameDao.addGame(game);
        List<Game> games = gameDao.findGamesByTitle("Fifa 2020");
        assertEquals(1, games.size());
        games = gameDao.findGamesByTitle("PES");
        assertEquals(0, games.size());
    }
}