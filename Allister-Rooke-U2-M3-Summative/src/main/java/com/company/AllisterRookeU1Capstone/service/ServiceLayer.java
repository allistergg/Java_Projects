package com.company.AllisterRookeU1Capstone.service;

import com.company.AllisterRookeU1Capstone.dao.*;
import com.company.AllisterRookeU1Capstone.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class ServiceLayer {

    @Autowired
    private ConsoleDao consoleDao;
    @Autowired
    private GameDao gameDao;
    @Autowired
    private InvoiceDao invoiceDao;
    @Autowired
    private ProcessingFeeDao processingFeeDao;
    @Autowired
    private SalesTaxRateDao salesTaxRateDao;
    @Autowired
    private TShirtDao tShirtDao;

    public ServiceLayer(ConsoleDao consoleDao, GameDao gameDao, InvoiceDao invoiceDao, ProcessingFeeDao processingFeeDao, SalesTaxRateDao salesTaxRateDao, TShirtDao tShirtDao) {
        this.consoleDao = consoleDao;
        this.gameDao = gameDao;
        this.invoiceDao = invoiceDao;
        this.processingFeeDao = processingFeeDao;
        this.salesTaxRateDao = salesTaxRateDao;
        this.tShirtDao = tShirtDao;
    }

    public Console addConsole(Console console) {
        return consoleDao.addConsole(console);
    }

    public Console findConsole(int consoleId) {
        return consoleDao.findConsole(consoleId);
    }

    public List<Console> findAllConsoles() {
        return consoleDao.findAllConsoles();
    }

    public void updateConsole(Console console) {
        consoleDao.updateConsole(console);
    }

    public void deleteConsole(int consoleId) {
        consoleDao.deleteConsole(consoleId);
    }


    public Game addGame(Game gameNoId) {
        return gameDao.addGame(gameNoId);
    }

    public Game findGame(int gameId) {
        return gameDao.findGame(gameId);
    }

    public List<Game> findAllGames() {
        return gameDao.findAllGames();
    }


    public void updateGame(Game game) {
        gameDao.updateGame(game);
    }

    public void deleteGame(int gameId) {
        gameDao.deleteGame(gameId);
    }

    public TShirt addTShirt(TShirt tShirtNoId) {
        return tShirtDao.addTShirt(tShirtNoId);
    }

    public TShirt findTShirt(int tShirtId) {
        return tShirtDao.findTShirt(tShirtId);
    }

    public List<TShirt> findAllTShirts() {
        return tShirtDao.findAllTShirts();
    }

    public void updateTShirt(TShirt tShirt) {
        tShirtDao.updateTShirt(tShirt);
    }

    public void deleteTShirt(int tShirtId) {
        tShirtDao.deleteTShirt(tShirtId);
    }

    public List<Console> findConsoleByManufacturer(String manufacturer) {
        return consoleDao.findConsoleByManufacturer(manufacturer);
    }

    public List<Game> findGamesByTitle(String title) {
        return gameDao.findGamesByTitle(title);
    }

    public List<Game> findGamesByEsrbRating(String esrbRating) {
        return gameDao.findGamesByEsrbRating(esrbRating);
    }

    public List<Game> findGamesByStudio(String studio) {
        return gameDao.findGamesByStudio(studio);
    }

    public List<TShirt> findTShirtsByColor(String color) {
        return tShirtDao.findTShirtByColor(color);
    }


    public List<TShirt> findTShirtsBySize(String size) {
        return tShirtDao.findTShirtBySize(size);
    }

    private Invoice buildInvoice(PurchaseOrder order) {


        SalesTaxRate salesTax = salesTaxRateDao.findRateByState(order.getState());
        ProcessingFee processingFee = processingFeeDao.findFeeByProductType(order.getItemType());
        if (order.getQuantity() > 10) {
            processingFee.setFee(processingFee.getFee().add(new BigDecimal("15.49")));
        }

        BigDecimal unitPrice = new BigDecimal("1");

        switch (order.getItemType()) {
            case "Consoles":

                Console console = consoleDao.findConsole(order.getItemId());

                if (console == null) {
                    throw new IllegalArgumentException("Console does not exist");
                }
                if (order.getQuantity() > console.getQuantity()) {
                    throw new IllegalArgumentException("Order quantity cannot be greater than inventory");
                }
                unitPrice = console.getPrice();
                console.setQuantity(console.getQuantity() - order.getQuantity());
                consoleDao.updateConsole(console);
                break;
            case "Games":
                Game game = gameDao.findGame(order.getItemId());

                if (game ==  null) {
                    throw new IllegalArgumentException("Game does not exist");
                }
                if (order.getQuantity() > game.getQuantity()) {
                    throw new IllegalArgumentException("Order quantity cannot be greater than inventory");
                }
                unitPrice = game.getPrice();
                game.setQuantity(game.getQuantity() - order.getQuantity());
                gameDao.updateGame(game);
                break;
            case "TShirts":
                TShirt tShirt = tShirtDao.findTShirt(order.getItemId());

                if (tShirt == null) {
                    throw new IllegalArgumentException("tShirt does not exist");
                }
                if (order.getQuantity() > tShirt.getQuantity()) {
                    throw new IllegalArgumentException("Order quantity cannot be greater than inventory");
                }
                unitPrice = tShirt.getPrice();
                tShirt.setQuantity(tShirt.getQuantity() - order.getQuantity());
                tShirtDao.updateTShirt(tShirt);
                break;
        }

        BigDecimal subtotal = unitPrice.multiply(new BigDecimal(order.getQuantity()));

        BigDecimal salesTaxAmount = subtotal.multiply(salesTax.getRate());

        BigDecimal total =  subtotal.add(salesTaxAmount).add(processingFee.getFee());

        Invoice invoice = new Invoice(order.getName(), order.getStreet(), order.getCity(), order.getState(), order.getZip(),
                order.getItemType(), order.getItemId(),unitPrice, order.getQuantity(), subtotal, salesTaxAmount, processingFee.getFee(), total);

        return invoice;
    }

    public Invoice createInvoice(PurchaseOrder order) throws IllegalArgumentException{

        if (order.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        String[] states = { "AK", "AL", "AR", "AS", "AZ", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "GU", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MP", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UM", "UT", "VA", "VI", "VT", "WA", "WI", "WV", "WY"};

        String[] itemTypes = {"Games", "Consoles", "TShirts"};

        if (!Arrays.asList(states).contains(order.getState())) {
            throw new IllegalArgumentException("Order must contain a valid state");
        }

        if (!Arrays.asList(itemTypes).contains(order.getItemType())) {
            throw new IllegalArgumentException("Invalid Item Type");
        }

        Invoice invoiceNoId = buildInvoice(order);

        Invoice invoice = invoiceDao.addInvoice(invoiceNoId);

        return invoice;

    }


}
