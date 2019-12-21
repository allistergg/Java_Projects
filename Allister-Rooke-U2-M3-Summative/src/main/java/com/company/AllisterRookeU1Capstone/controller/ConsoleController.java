package com.company.AllisterRookeU1Capstone.controller;

import com.company.AllisterRookeU1Capstone.model.Console;
import com.company.AllisterRookeU1Capstone.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ConsoleController {

    @Autowired
    private ServiceLayer service;

    @RequestMapping(value="/console", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Console> getAllConsoles() {
        return service.findAllConsoles();
    }

    @RequestMapping(value="/console/{id}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Console getConsole(@PathVariable Integer id) {

        Console console = service.findConsole(id);

        if (console == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Console not found");
        }

        return console;
    }

    @RequestMapping(value="/console", method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Console addConsole(@RequestBody @Valid Console console) {
        return service.addConsole(console);
    }

    @RequestMapping(value="/console/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConsole(@PathVariable Integer id) {
        service.deleteConsole(id);
    }

    @RequestMapping(value="/console/{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateConsole(@PathVariable Integer id, @RequestBody @Valid Console console) throws IllegalArgumentException{
        if (id == console.getConsoleId()) {
            service.updateConsole(console);
        } else {
            throw new IllegalArgumentException("Id does not match");
        }
    }

    @RequestMapping(value="/console/manufacturer/{manufacturer}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Console> getConsolesByManufacturer(@PathVariable String manufacturer) {
        return service.findConsoleByManufacturer(manufacturer);
    }


}
