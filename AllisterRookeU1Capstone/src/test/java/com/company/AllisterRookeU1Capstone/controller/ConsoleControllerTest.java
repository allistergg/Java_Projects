package com.company.AllisterRookeU1Capstone.controller;

import com.company.AllisterRookeU1Capstone.model.Console;
import com.company.AllisterRookeU1Capstone.service.ServiceLayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ConsoleController.class)
public class ConsoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceLayer service;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getAllConsoles() throws Exception{
        Console console =
                new Console(1, "XBox", "Microsoft", "8GB", "Intel", new BigDecimal("199.99"), 10);
        List<Console> consoles = Collections.singletonList(console);

        String outputJson = mapper.writeValueAsString(consoles);

        when(service.findAllConsoles()).thenReturn(consoles);


        this.mockMvc.perform(get("/console/"))

                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));




    }

    @Test
    public void getConsole() throws Exception {
        Console console =
                new Console(1, "XBox", "Microsoft", "8GB", "Intel", new BigDecimal("199.99"), 10);

        String outputJson = mapper.writeValueAsString(console);

        when(service.findConsole(console.getConsoleId())).thenReturn(console);

        this.mockMvc.perform(get("/console/1"))

                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

        this.mockMvc.perform(get("/console/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addConsole() throws Exception {

        Console consoleNoId =
                new Console( "XBox", "Microsoft", "8GB", "Intel", new BigDecimal("199.99"), 10);

        Console console =
                new Console(1, "XBox", "Microsoft", "8GB", "Intel", new BigDecimal("199.99"), 10);

        String inputJson = mapper.writeValueAsString(consoleNoId);
        String outputJson = mapper.writeValueAsString(console);

        when(service.addConsole(consoleNoId)).thenReturn(console);

        this.mockMvc.perform(post("/console/")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));

        consoleNoId.setModel(null);
        inputJson = mapper.writeValueAsString(consoleNoId);

        this.mockMvc.perform(post("/console/")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        consoleNoId.setModel("XBox");
        consoleNoId.setQuantity(0);

        inputJson = mapper.writeValueAsString(consoleNoId);

        when(service.addConsole(consoleNoId)).thenThrow(new IllegalArgumentException());

        this.mockMvc.perform(post("/console/")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        inputJson = "bad json";

        this.mockMvc.perform(post("/console/")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }





    @Test
    public void deleteConsole() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/console/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void updateConsole() throws Exception {
        Console console =
                new Console(1, "XBox", "Microsoft", "8GB", "Intel", new BigDecimal("199.99"), 15);

        String inputJson = mapper.writeValueAsString(console);

        this.mockMvc.perform(put("/console/1")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        this.mockMvc.perform(put("/console/9999")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void getConsolesByManufacturer() throws Exception {

        Console console =
                new Console(1, "XBox", "Microsoft", "8GB", "Intel", new BigDecimal("199.99"), 15);

        List<Console> consoles = Collections.singletonList(console);

        String outputJson = mapper.writeValueAsString(consoles);

        when(service.findConsoleByManufacturer(console.getManufacturer())).thenReturn(consoles);

        this.mockMvc.perform(get("/console/manufacturer/Microsoft"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

}