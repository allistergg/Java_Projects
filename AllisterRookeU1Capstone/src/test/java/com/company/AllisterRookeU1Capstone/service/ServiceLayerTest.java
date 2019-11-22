package com.company.AllisterRookeU1Capstone.service;

import com.company.AllisterRookeU1Capstone.dao.*;
import com.company.AllisterRookeU1Capstone.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceLayerTest {

    private ConsoleDao consoleDao;
    private GameDao gameDao;
    private InvoiceDao invoiceDao;
    private ProcessingFeeDao processingFeeDao;
    private SalesTaxRateDao salesTaxRateDao;
    private TShirtDao tShirtDao;
    private ServiceLayer service;

    public void setUpConsoleDaoMock() {
        consoleDao = mock(ConsoleDao.class);
        Console console = new Console(1, "Playstation", "Sony", "8GB", "Intel", new BigDecimal("199.99"), 12);
        Console consoleNoId = new Console("Playstation", "Sony", "8GB", "Intel", new BigDecimal("199.99"), 12);
        List<Console> consoles = Collections.singletonList(console);


        doReturn(console).when(consoleDao).findConsole(console.getConsoleId());
        doReturn(console).when(consoleDao).addConsole(consoleNoId);
        doReturn(consoles).when(consoleDao).findAllConsoles();
        doReturn(consoles).when(consoleDao).findConsoleByManufacturer(console.getManufacturer());


    }

    public void setUpGameDaoMock() {
        gameDao = mock(GameDao.class);
        Game game = new Game(10, "Fifa 2020", "AA", "Soccer", new BigDecimal("49.99"), "EA Sports", 20);
        Game gameNoId = new Game("Fifa 2020", "AA", "Soccer", new BigDecimal("49.99"),  "EA Sports", 20 );
        List<Game> games = Collections.singletonList(game);

        doReturn(game).when(gameDao).findGame(game.getGameId());
        doReturn(game).when(gameDao).addGame(gameNoId);
        doReturn(games).when(gameDao).findAllGames();
        doReturn(games).when(gameDao).findGamesByEsrbRating(game.getEsrbRating());
        doReturn(games).when(gameDao).findGamesByStudio(game.getStudio());
        doReturn(games).when(gameDao).findGamesByTitle(game.getTitle());
    }

    public void setUpTShirtDaoMock() {
        tShirtDao = mock(TShirtDao.class);

        TShirt tShirt = new TShirt(101, "M", "green", "plain", new BigDecimal("9.99"), 15);
        TShirt tShirtNoId = new TShirt("M", "green", "plain", new BigDecimal("9.99"), 15);
        List<TShirt> tShirts = Collections.singletonList(tShirt);

        doReturn(tShirt).when(tShirtDao).findTShirt(tShirt.getTShirtId());
        doReturn(tShirt).when(tShirtDao).addTShirt(tShirtNoId);
        doReturn(tShirts).when(tShirtDao).findAllTShirts();
        doReturn(tShirts).when(tShirtDao).findTShirtByColor(tShirt.getColor());
        doReturn(tShirts).when(tShirtDao).findTShirtBySize(tShirt.getSize());

    }

    public void setUpInvoiceDaoMock() {
        invoiceDao = mock(InvoiceDao.class);

        Invoice invoice = new Invoice(1001, "John", "5th Street", "Atlanta", "GA", "30303", "Consoles", 1, new BigDecimal("199.99"),
                1, new BigDecimal("199.99"), new BigDecimal("13.9993"), new BigDecimal("14.99"), new BigDecimal("228.9793"));

        Invoice invoiceNoId = new Invoice("John", "5th Street", "Atlanta", "GA", "30303", "Consoles", 1, new BigDecimal("199.99"),
                1, new BigDecimal("199.99"), new BigDecimal("13.9993"), new BigDecimal("14.99"), new BigDecimal("228.9793"));

        Invoice invoice2 = new Invoice(1002, "John", "5th Street", "Atlanta", "GA" , "30303", "Consoles", 1, new BigDecimal("199.99"), 11, new BigDecimal("2199.89"),new BigDecimal("153.9923"),
                new BigDecimal("30.48"), new BigDecimal("2384.3623"));

        Invoice invoiceNoId2 = new Invoice("John", "5th Street", "Atlanta", "GA" , "30303", "Consoles", 1, new BigDecimal("199.99"), 11, new BigDecimal("2199.89"),new BigDecimal("153.9923"),
                new BigDecimal("30.48"), new BigDecimal("2384.3623"));

        Invoice invoice3 = new Invoice(1003, "John", "5th Street", "Atlanta", "GA", "30303", "Games", 10, new BigDecimal("49.99"),
                1, new BigDecimal("49.99"), new BigDecimal("3.4993"), new BigDecimal("1.49"), new BigDecimal("54.9793"));
        Invoice invoiceNoId3 = new Invoice("John", "5th Street", "Atlanta", "GA", "30303", "Games", 10, new BigDecimal("49.99"),
                1, new BigDecimal("49.99"), new BigDecimal("3.4993"), new BigDecimal("1.49"), new BigDecimal("54.9793"));

        Invoice invoice4 = new Invoice(1004, "John", "5th Street", "Atlanta", "GA", "30303", "Games", 10, new BigDecimal("49.99"),
                11, new BigDecimal("549.89"), new BigDecimal("38.4923"), new BigDecimal("16.98"), new BigDecimal("605.3623"));
        Invoice invoiceNoId4 = new Invoice("John", "5th Street", "Atlanta", "GA", "30303", "Games", 10, new BigDecimal("49.99"),
                11, new BigDecimal("549.89"), new BigDecimal("38.4923"), new BigDecimal("16.98"), new BigDecimal("605.3623"));

        Invoice invoice5 = new Invoice(1005, "John", "5th Street", "Atlanta", "GA", "30303", "TShirts", 101, new BigDecimal("9.99"),
                1, new BigDecimal("9.99"), new BigDecimal(".6993"), new BigDecimal("1.98"), new BigDecimal("12.6693"));
        Invoice invoiceNoId5 = new Invoice("John", "5th Street", "Atlanta", "GA", "30303", "TShirts", 101, new BigDecimal("9.99"),
                1, new BigDecimal("9.99"), new BigDecimal(".6993"), new BigDecimal("1.98"), new BigDecimal("12.6693"));

        Invoice invoice6 = new Invoice(1006, "John", "5th Street", "Atlanta", "GA", "30303", "TShirts", 101, new BigDecimal("9.99"),
                11, new BigDecimal("109.89"), new BigDecimal("7.6923"), new BigDecimal("17.47"), new BigDecimal("135.0523"));
        Invoice invoiceNoId6 = new Invoice("John", "5th Street", "Atlanta", "GA", "30303", "TShirts", 101, new BigDecimal("9.99"),
                11, new BigDecimal("109.89"), new BigDecimal("7.6923"), new BigDecimal("17.47"), new BigDecimal("135.0523"));

        List<Invoice> invoices = Collections.singletonList(invoice);

        doReturn(invoice).when(invoiceDao).findInvoice(invoice.getInvoiceId());
        doReturn(invoice).when(invoiceDao).addInvoice(invoiceNoId);
        doReturn(invoices).when(invoiceDao).findAllInvoices();
        doReturn(invoice2).when(invoiceDao).addInvoice(invoiceNoId2);
        doReturn(invoice3).when(invoiceDao).addInvoice(invoiceNoId3);
        doReturn(invoice4).when(invoiceDao).addInvoice(invoiceNoId4);
        doReturn(invoice5).when(invoiceDao).addInvoice(invoiceNoId5);
        doReturn(invoice6).when(invoiceDao).addInvoice(invoiceNoId6);

    }

    public void setUpProcessingFeeDaoMock() {
        processingFeeDao = mock(ProcessingFeeDao.class);

        ProcessingFee pf = new ProcessingFee();
        pf.setProductType("Consoles");
        pf.setFee(new BigDecimal("14.99"));

        ProcessingFee pf2 =  new ProcessingFee();
        pf2.setProductType("Games");
        pf2.setFee(new BigDecimal("1.49"));

        ProcessingFee pf3 = new ProcessingFee();
        pf3.setProductType("TShirts");
        pf3.setFee(new BigDecimal("1.98"));



        doReturn(pf).when(processingFeeDao).findFeeByProductType(pf.getProductType());
        doReturn(pf2).when(processingFeeDao).findFeeByProductType(pf2.getProductType());
        doReturn(pf3).when(processingFeeDao).findFeeByProductType(pf3.getProductType());

    }

    public void setUpSalesTaxRateDaoMock() {
        salesTaxRateDao = mock(SalesTaxRateDao.class);
        SalesTaxRate gaTax = new SalesTaxRate();
        gaTax.setRate(new BigDecimal(".07"));
        gaTax.setState("GA");

        doReturn(gaTax).when(salesTaxRateDao).findRateByState(gaTax.getState());
    }



    @Before
    public void setUp() throws Exception {

        setUpConsoleDaoMock();
        setUpGameDaoMock();
        setUpInvoiceDaoMock();
        setUpTShirtDaoMock();
        setUpProcessingFeeDaoMock();
        setUpSalesTaxRateDaoMock();

        service = new ServiceLayer(consoleDao, gameDao, invoiceDao, processingFeeDao, salesTaxRateDao, tShirtDao);
    }

    @Test
    public void addConsole() {
        Console consoleNoId = new Console("Playstation", "Sony", "8GB", "Intel", new BigDecimal("199.99"), 12);
        Console console = new Console(1, "Playstation", "Sony", "8GB", "Intel", new BigDecimal("199.99"), 12);
        Console fromService = service.addConsole(consoleNoId);
        assertEquals(console, fromService);
    }

    @Test
    public void findConsole() {
        Console console = new Console(1, "Playstation", "Sony", "8GB", "Intel", new BigDecimal("199.99"), 12);
        Console fromService = service.findConsole(console.getConsoleId());
        assertEquals(console, fromService);
    }

    @Test
    public void findAllConsoles() {
        Console console = new Console(1, "Playstation", "Sony", "8GB", "Intel", new BigDecimal("199.99"), 12);
        List<Console> consoles = Collections.singletonList(console);
        List<Console> fromService = service.findAllConsoles();
        assertEquals(consoles, fromService);
    }

    @Test
    public void updateConsole() {
        Console console = new Console(1, "Playstation", "Sony", "8GB", "Intel", new BigDecimal("199.99"), 12);
        ArgumentCaptor<Console> captor = ArgumentCaptor.forClass(Console.class);
        service.updateConsole(console);
        verify(consoleDao, times(1)).updateConsole(captor.capture());
        assertEquals(console, captor.getValue());

    }

    @Test
    public void deleteConsole() {
        int consoleId = 1;
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        service.deleteConsole(consoleId);
        verify(consoleDao, times(1)).deleteConsole(captor.capture());
        assertEquals(captor.getValue().intValue(), consoleId);
    }

    @Test
    public void addGame() {
        Game gameNoId = new Game("Fifa 2020", "AA", "Soccer", new BigDecimal("49.99"), "EA Sports", 20);
        Game game = new Game(10, "Fifa 2020", "AA", "Soccer", new BigDecimal("49.99"), "EA Sports", 20);
        Game fromService = service.addGame(gameNoId);
        assertEquals(game, fromService);
    }

    @Test
    public void findGame() {
        Game game = new Game(10, "Fifa 2020", "AA", "Soccer", new BigDecimal("49.99"), "EA Sports", 20);
        Game fromService = service.findGame(game.getGameId());
        assertEquals(game, fromService);
    }

    @Test
    public void findAllGames() {
        Game game = new Game(10, "Fifa 2020", "AA", "Soccer", new BigDecimal("49.99"), "EA Sports", 20);
        List<Game> games =  Collections.singletonList(game);
        List<Game> fromService = service.findAllGames();
        assertEquals(games, fromService);
    }

    @Test
    public void updateGame() {
        Game game = new Game(10, "Fifa 2020", "AA", "Soccer", new BigDecimal("49.99"), "EA Sports", 20);
        ArgumentCaptor<Game> captor = ArgumentCaptor.forClass(Game.class);
        service.updateGame(game);
        verify(gameDao, times(1)).updateGame(captor.capture());
        assertEquals(game, captor.getValue());


    }

    @Test
    public void deleteGame() {
        int gameId = 10;
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        service.deleteGame(gameId);
        verify(gameDao, times(1)).deleteGame(captor.capture());
        assertEquals(gameId, captor.getValue().intValue());
    }

    @Test
    public void addTShirt() {
        TShirt tShirt = new TShirt(101, "M", "green", "plain", new BigDecimal("9.99"), 15);
        TShirt tShirtNoId = new TShirt("M", "green", "plain", new BigDecimal("9.99"), 15);
        TShirt fromService = service.addTShirt(tShirtNoId);
        assertEquals(tShirt, fromService);
    }

    @Test
    public void findTShirt() {
        TShirt tShirt = new TShirt(101, "M", "green", "plain", new BigDecimal("9.99"), 15);
        TShirt fromService = service.findTShirt(tShirt.getTShirtId());
        assertEquals(tShirt, fromService);
    }

    @Test
    public void findAllTShirts() {
        TShirt tShirt = new TShirt(101, "M", "green", "plain", new BigDecimal("9.99"), 15);
        List<TShirt> tShirts = Collections.singletonList(tShirt);
        List<TShirt> fromService = service.findAllTShirts();
        assertEquals(tShirts, fromService);
    }

    @Test
    public void updateTShirt() {
        TShirt tShirt = new TShirt(101, "M", "green", "plain", new BigDecimal("9.99"), 15);
        ArgumentCaptor<TShirt> captor = ArgumentCaptor.forClass(TShirt.class);
        service.updateTShirt(tShirt);
        verify(tShirtDao, times(1)).updateTShirt(captor.capture());
        assertEquals(tShirt, captor.getValue());
    }

    @Test
    public void deleteTShirt() {

        int tShirtId = 101;
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        service.deleteTShirt(tShirtId);
        verify(tShirtDao, times(1)).deleteTShirt(captor.capture());
        assertEquals(tShirtId, captor.getValue().intValue());

    }

    @Test
    public void findConsoleByManufacturer() {
        Console console = new Console(1, "Playstation", "Sony", "8GB", "Intel", new BigDecimal("199.99"), 12);
        List<Console> consoles = Collections.singletonList(console);
        List<Console> fromService = service.findConsoleByManufacturer(console.getManufacturer());
        assertEquals(consoles, fromService);
        fromService = service.findConsoleByManufacturer("Not an existing manufacturer");
        assertEquals(0, fromService.size());
    }

    @Test
    public void findGamesByTitle() {
        Game game = new Game(10, "Fifa 2020", "AA", "Soccer", new BigDecimal("49.99"), "EA Sports", 20);
        List<Game> games = Collections.singletonList(game);
        List<Game> fromService = service.findGamesByTitle(game.getTitle());
        assertEquals(games, fromService);
    }

    @Test
    public void findGamesByEsrbRating() {
        Game game = new Game(10, "Fifa 2020", "AA", "Soccer", new BigDecimal("49.99"), "EA Sports", 20);
        List<Game> games = Collections.singletonList(game);
        List<Game> fromService = service.findGamesByEsrbRating(game.getEsrbRating());
        assertEquals(games, fromService);
    }

    @Test
    public void findGamesByStudio() {
        Game game = new Game(10, "Fifa 2020", "AA", "Soccer", new BigDecimal("49.99"), "EA Sports", 20);
        List<Game> games = Collections.singletonList(game);
        List<Game> fromService = service.findGamesByStudio(game.getStudio());
        assertEquals(games, fromService);
    }

    @Test
    public void findTShirtsByColor() {
        TShirt tShirt = new TShirt(101, "M", "green", "plain", new BigDecimal("9.99"), 15);
        List<TShirt> tShirts = Collections.singletonList(tShirt);
        List<TShirt> fromService = service.findTShirtsByColor(tShirt.getColor());
        assertEquals(tShirts, fromService);
    }

    @Test
    public void findTShirtBySize() {
        TShirt tShirt = new TShirt(101, "M", "green", "plain", new BigDecimal("9.99"), 15);
        List<TShirt> tShirts = Collections.singletonList(tShirt);
        List<TShirt> fromService = service.findTShirtsBySize(tShirt.getSize());
        assertEquals(tShirts, fromService);
    }

    @Test
    public void addInvoice() {
        PurchaseOrder order = new PurchaseOrder("John", "5th Street", "Atlanta", "GA", "30303", "Consoles", 1, 1);
        PurchaseOrder order2 = new PurchaseOrder("John", "5th Street", "Atlanta", "GA", "30303", "Games", 10, 1);
        PurchaseOrder order3 = new PurchaseOrder("John", "5th Street", "Atlanta", "GA", "30303", "TShirts", 101, 1);
        Invoice invoice = new Invoice(1001, "John", "5th Street", "Atlanta", "GA", "30303", "Consoles", 1, new BigDecimal("199.99"),
               1, new BigDecimal("199.99"), new BigDecimal("13.9993"), new BigDecimal("14.99"), new BigDecimal("228.9793"));

        Invoice invoice2 = new Invoice(1002, "John", "5th Street", "Atlanta", "GA" , "30303", "Consoles", 1, new BigDecimal("199.99"), 11, new BigDecimal("2199.89"),new BigDecimal("153.9923"),
                new BigDecimal("30.48"), new BigDecimal("2384.3623"));

        Invoice invoice3 = new Invoice(1003, "John", "5th Street", "Atlanta", "GA", "30303", "Games", 10, new BigDecimal("49.99"),
                1, new BigDecimal("49.99"), new BigDecimal("3.4993"), new BigDecimal("1.49"), new BigDecimal("54.9793"));

        Invoice invoice4 = new Invoice(1004, "John", "5th Street", "Atlanta", "GA", "30303", "Games", 10, new BigDecimal("49.99"),
                11, new BigDecimal("549.89"), new BigDecimal("38.4923"), new BigDecimal("16.98"), new BigDecimal("605.3623"));

        Invoice invoice5 = new Invoice(1005, "John", "5th Street", "Atlanta", "GA", "30303", "TShirts", 101, new BigDecimal("9.99"),
                1, new BigDecimal("9.99"), new BigDecimal(".6993"), new BigDecimal("1.98"), new BigDecimal("12.6693"));

        Invoice invoice6 = new Invoice(1006, "John", "5th Street", "Atlanta", "GA", "30303", "TShirts", 101, new BigDecimal("9.99"),
                11, new BigDecimal("109.89"), new BigDecimal("7.6923"), new BigDecimal("17.47"), new BigDecimal("135.0523"));

        Invoice fromService = service.createInvoice(order);

        assertEquals(invoice, fromService);

        order.setQuantity(11);

        fromService = service.createInvoice(order);

        assertEquals(invoice2, fromService);

        order.setQuantity(0);

        boolean errorThrown = false;

        try {
            fromService = service.createInvoice(order);
        } catch (IllegalArgumentException e) {
            errorThrown = true;
        }

        assertTrue(errorThrown);

        order.setQuantity(15);
        errorThrown = false;

        try {
            fromService = service.createInvoice(order);
        } catch (IllegalArgumentException e) {
            errorThrown = true;
        }

        assertTrue(errorThrown);

        fromService = service.createInvoice(order2);

        assertEquals(fromService, invoice3);

        order2.setQuantity(11);

        fromService = service.createInvoice(order2);

        assertEquals(invoice4, fromService);

        order2.setQuantity(20);

        errorThrown = false;

        try {
            fromService = service.createInvoice(order2);
        } catch( IllegalArgumentException e) {
            errorThrown = true;
        }

        assertTrue(errorThrown);

        fromService = service.createInvoice(order3);

        assertEquals(fromService, invoice5);

        order3.setQuantity(11);

        fromService = service.createInvoice(order3);

        assertEquals(fromService, invoice6);

        order3.setQuantity(20);

        errorThrown = false;

        try {
            fromService = service.createInvoice(order3);
        } catch (IllegalArgumentException e) {
            errorThrown = true;
        }

        assertTrue(errorThrown);

        order3.setQuantity(1);
        order3.setState("INVALID STATE");
        errorThrown = false;

        try {
            fromService = service.createInvoice(order3);
        } catch(IllegalArgumentException e) {
            errorThrown = true;
        }

        assertTrue(errorThrown);

        errorThrown = false;
        ;
        order.setItemId(0);
        order2.setQuantity(1);
        order2.setItemId(0);
        order3.setItemId(0);
        order3.setState("GA");

        try {
            fromService = service.createInvoice(order);
        } catch (IllegalArgumentException e) {
            errorThrown = true;
        }

        assertTrue(errorThrown);
        errorThrown = false;

        try {
            fromService = service.createInvoice(order2);
        } catch (IllegalArgumentException e) {
            errorThrown = true;
        }

        assertTrue(errorThrown);
        errorThrown = false;

        try {
            fromService = service.createInvoice(order3);
        } catch (IllegalArgumentException e) {
            errorThrown = true;
        }

        assertTrue(errorThrown);

        errorThrown = false;


        order2.setItemId(10);
        order2.setItemType("INVALID");

        try {
            fromService = service.createInvoice(order2);
        } catch (IllegalArgumentException e) {
            errorThrown = true;
        }

        assertTrue(errorThrown);

    }

   @Test
    public void findInvoice() {

   }











}