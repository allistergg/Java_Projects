package com.company.AllisterRookeU1Capstone.controller;

import com.company.AllisterRookeU1Capstone.model.Game;
import com.company.AllisterRookeU1Capstone.model.TShirt;
import com.company.AllisterRookeU1Capstone.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TShirtController {

    @Autowired
    private ServiceLayer service;

    @RequestMapping(value="/tshirt", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<TShirt> getAllTshirts() {
        return service.findAllTShirts();
    }

    @RequestMapping(value="/tshirt/{id}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public TShirt getTShirt(@PathVariable Integer id) {

        TShirt tShirt = service.findTShirt(id);

        if (tShirt == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "TShirt not found");
        }
        return tShirt;

    }

    @RequestMapping(value="/tshirt", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public TShirt addTShirt(@RequestBody @Valid TShirt tShirt) {
        return service.addTShirt(tShirt);
    }

    @RequestMapping(value="/tshirt/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTShirt(@PathVariable Integer id) {
        service.deleteTShirt(id);
    }

    @RequestMapping(value="/tshirt/{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTShirt(@PathVariable Integer id, @RequestBody @Valid TShirt tShirt) throws IllegalArgumentException{
        if (id == tShirt.getTShirtId()) {
            service.updateTShirt(tShirt);
        } else {
            throw new IllegalArgumentException("Id does not match");
        }
    }

    @RequestMapping(value="/tshirt/color/{color}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<TShirt> getTShirtsByColor(@PathVariable String color) {
        return service.findTShirtsByColor(color);
    }

    @RequestMapping(value="/tshirt/size/{size}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<TShirt> getTShirtsBySize(@PathVariable String size) {
        return service.findTShirtsBySize(size);
    }

}
