package com.company.AllisterRookeU1Capstone.dao;

import com.company.AllisterRookeU1Capstone.model.Game;

import java.util.List;

public interface GameDao {

    Game findGame(int id);

    List<Game> findAllGames();

    Game addGame(Game game);

    void updateGame(Game game);

    void deleteGame(int id);

    List<Game> findGamesByStudio(String studio);

    List<Game> findGamesByEsrbRating(String esrbRating);

    List<Game> findGamesByTitle(String title);
}
