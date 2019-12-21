package com.company.AllisterRookeU1Capstone.controller;

import com.company.AllisterRookeU1Capstone.model.Game;
import com.company.AllisterRookeU1Capstone.service.ServiceLayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerTest {

    @MockBean
    DataSource dataSource;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceLayer service;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getAllGames() throws Exception {
        Game game = new Game("Fifa 2020", "AA", "soccer", new BigDecimal("9.99"), "EA Sports", 4);

        List<Game> games = Collections.singletonList(game);

        String outputJson = mapper.writeValueAsString(games);

        when(service.findAllGames()).thenReturn(games);


        this.mockMvc.perform(get("/game/"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void getGame() throws Exception{

        Game game = new Game(10,"Fifa 2020", "AA", "soccer", new BigDecimal("9.99"), "EA Sports", 4);

        String outputJson = mapper.writeValueAsString(game);

        when(service.findGame(game.getGameId())).thenReturn(game);

        this.mockMvc.perform(get("/game/10"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

        this.mockMvc.perform(get("/game/9999"))
                .andExpect(status().isNotFound());

    }

    @Test
    @WithMockUser(username="managerUser", roles = {"MANAGER"})
    public void addGame() throws Exception {

        Game game = new Game(10,"Fifa 2020", "AA", "soccer", new BigDecimal("9.99"), "EA Sports", 4);

        Game gameNoId = new Game("Fifa 2020", "AA", "soccer", new BigDecimal("9.99"), "EA Sports", 4);

        String inputJson = mapper.writeValueAsString(gameNoId);
        String outputJson = mapper.writeValueAsString(game);

        when(service.addGame(gameNoId)).thenReturn(game);

        this.mockMvc.perform(post("/game/")
                .with(csrf().asHeader())
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    @WithMockUser(username="adminUser", roles = {"ADMIN"})
    public void deleteGame() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/game/10")
                .with(csrf().asHeader()))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    @WithMockUser(username="staffUser", roles = {"STAFF"})
    public void updateGame() throws Exception {

        Game game = new Game(10,"Fifa 2020", "AA", "soccer", new BigDecimal("9.99"), "EA Sports", 4);

        String inputJson = mapper.writeValueAsString(game);

        this.mockMvc.perform(put("/game/10")
                .with(csrf().asHeader())
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(put("/game/9999")
                .with(csrf().asHeader())
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());


    }

    @Test
    public void getGamesByEsrb() throws Exception{

        Game game = new Game("Fifa 2020", "AA", "soccer", new BigDecimal("9.99"), "EA Sports", 4);

        List<Game> games = Collections.singletonList(game);

        String outputJson = mapper.writeValueAsString(games);

        when(service.findGamesByEsrbRating(game.getEsrbRating())).thenReturn(games);

        this.mockMvc.perform(get("/game/esrb/AA"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void getGamesByStudio() throws Exception {

        Game game = new Game("Fifa 2020", "AA", "soccer", new BigDecimal("9.99"), "EA Sports", 4);

        List<Game> games = Collections.singletonList(game);

        String outputJson = mapper.writeValueAsString(games);

        when(service.findGamesByStudio(game.getStudio())).thenReturn(games);

        this.mockMvc.perform(get("/game/studio/EA Sports"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void getGamesByTitle() throws Exception{

        Game game = new Game("Fifa 2020", "AA", "soccer", new BigDecimal("9.99"), "EA Sports", 4);

        List<Game> games = Collections.singletonList(game);

        String outputJson = mapper.writeValueAsString(games);

        when(service.findGamesByTitle(game.getTitle())).thenReturn(games);

        this.mockMvc.perform(get("/game/title/Fifa 2020"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }
}