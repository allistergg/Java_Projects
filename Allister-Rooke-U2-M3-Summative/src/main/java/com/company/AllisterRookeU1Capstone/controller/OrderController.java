package com.company.AllisterRookeU1Capstone.controller;

import com.company.AllisterRookeU1Capstone.model.Invoice;
import com.company.AllisterRookeU1Capstone.model.PurchaseOrder;
import com.company.AllisterRookeU1Capstone.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class OrderController {

    @Autowired
    ServiceLayer service;

    @RequestMapping(value="/order", method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice submitPurchaseOrder(@RequestBody @Valid PurchaseOrder order) {
        System.out.println(order);
        return service.createInvoice(order);
    }
}
