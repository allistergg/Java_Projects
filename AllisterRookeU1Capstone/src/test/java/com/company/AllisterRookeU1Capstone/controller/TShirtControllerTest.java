package com.company.AllisterRookeU1Capstone.controller;

import com.company.AllisterRookeU1Capstone.model.TShirt;
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
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(TShirtController.class)
public class TShirtControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceLayer service;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getAllTshirts() throws Exception{
        TShirt tShirt = new TShirt(101,"M", "green", "blank", new BigDecimal("9.99"), 10);

        List<TShirt> tShirts = Collections.singletonList(tShirt);

        String outputJson = mapper.writeValueAsString(tShirts);

        when(service.findAllTShirts()).thenReturn(tShirts);


        this.mockMvc.perform(get("/tshirt/"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void getTShirt() throws Exception{

        TShirt tShirt = new TShirt(101,"M", "green", "blank", new BigDecimal("9.99"), 10);

        String outputJson = mapper.writeValueAsString(tShirt);

        when(service.findTShirt(tShirt.getTShirtId())).thenReturn(tShirt);

        this.mockMvc.perform(get("/tshirt/101"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

        this.mockMvc.perform(get("/tshirt/9999"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void addTShirt() throws Exception{

        TShirt tShirt = new TShirt(101,"M", "green", "blank", new BigDecimal("9.99"), 10);

        TShirt tShirtNoId = new TShirt("M", "green", "blank", new BigDecimal("9.99"), 10);

        String inputJson = mapper.writeValueAsString(tShirtNoId);
        String outputJson = mapper.writeValueAsString(tShirt);

        when(service.addTShirt(tShirtNoId)).thenReturn(tShirt);

        this.mockMvc.perform(post("/tshirt/")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void deleteTShirt() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/tshirt/101"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void updateTShirt() throws Exception {

        TShirt tShirt = new TShirt(101,"M", "green", "blank", new BigDecimal("9.99"), 10);

        String inputJson = mapper.writeValueAsString(tShirt);

        this.mockMvc.perform(put("/tshirt/101")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(put("/tshirt/9999")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void getTShirtsByColor() throws Exception {

        TShirt tShirt = new TShirt(101,"M", "green", "blank", new BigDecimal("9.99"), 10);

        List<TShirt> tShirts = Collections.singletonList(tShirt);

        String outputJson = mapper.writeValueAsString(tShirts);

        when(service.findTShirtsByColor(tShirt.getColor())).thenReturn(tShirts);


        this.mockMvc.perform(get("/tshirt/color/green"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void getTShirtsBySize() throws Exception {

        TShirt tShirt = new TShirt(101,"M", "green", "blank", new BigDecimal("9.99"), 10);

        List<TShirt> tShirts = Collections.singletonList(tShirt);

        String outputJson = mapper.writeValueAsString(tShirts);

        when(service.findTShirtsBySize(tShirt.getSize())).thenReturn(tShirts);


        this.mockMvc.perform(get("/tshirt/size/M"))
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

}