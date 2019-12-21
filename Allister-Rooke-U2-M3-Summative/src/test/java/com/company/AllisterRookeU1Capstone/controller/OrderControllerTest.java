package com.company.AllisterRookeU1Capstone.controller;

import com.company.AllisterRookeU1Capstone.model.Invoice;
import com.company.AllisterRookeU1Capstone.model.PurchaseOrder;
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

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceLayer service;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void submitPurchaseOrder() throws Exception {

        PurchaseOrder order = new PurchaseOrder("John", "5th Street", "Atlanta", "GA", "30303", "Consoles", 1, 1);

        Invoice invoice = new Invoice(1001, "John", "5th Street", "Atlanta", "GA", "30303", "Consoles", 1, new BigDecimal("199.99"),
                1, new BigDecimal("199.99"), new BigDecimal("13.9993"), new BigDecimal("14.99"), new BigDecimal("228.9793"));

        String inputJson = mapper.writeValueAsString(order);
        String outputJson = mapper.writeValueAsString(invoice);

        when(service.createInvoice(order)).thenReturn(invoice);

        this.mockMvc.perform(post("/order")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }
}