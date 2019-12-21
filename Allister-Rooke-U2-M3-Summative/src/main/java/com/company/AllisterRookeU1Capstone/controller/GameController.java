package com.company.AllisterRookeU1Capstone.controller;

import com.company.AllisterRookeU1Capstone.model.Game;
import com.company.AllisterRookeU1Capstone.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class GameController {

    @Autowired
    private ServiceLayer service;

    @RequestMapping(value="/game", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getAllGames() {
        return service.findAllGames();
    }

    @RequestMapping(value="/game/{id}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Game getGame(@PathVariable Integer id) {

        Game game = service.findGame(id);

        if (game == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Game not found");
        }
        return game;
    }

    @RequestMapping(value="/game", method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Game addGame(@RequestBody @Valid Game game) {
        return service.addGame(game);
    }

    @RequestMapping(value="/game/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable Integer id) {
        service.deleteGame(id);
    }

    @RequestMapping(value="/game/{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGame(@PathVariable Integer id, @RequestBody @Valid Game game) throws IllegalArgumentException{
        if (id == game.getGameId()) {
            service.updateGame(game);
        } else {
            throw new IllegalArgumentException("Id does not match");
        }
    }

    @RequestMapping(value="/game/esrb/{esrb}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGamesByEsrb(@PathVariable String esrb) {
        return service.findGamesByEsrbRating(esrb);
    }

    @RequestMapping(value="/game/studio/{studio}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGamesByStudio(@PathVariable String studio) {
        return service.findGamesByStudio(studio);
    }
    @RequestMapping(value="/game/title/{title}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getGamesByTitle(@PathVariable String title) {
        return service.findGamesByTitle(title);
    }

}
