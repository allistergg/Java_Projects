package com.company.AllisterRookeU1Capstone.dao;

import com.company.AllisterRookeU1Capstone.model.Invoice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceDaoTest {

    @Autowired
    InvoiceDao invoiceDao;

    Invoice invoice;

    @Before
    public void setUp() throws Exception {

        List<Invoice> invoices = invoiceDao.findAllInvoices();

        for (Invoice invoice : invoices) {
            invoiceDao.deleteInvoice(invoice.getInvoiceId());
        }

        invoice = new Invoice("John", "West 5th Street", "Atlanta", "GA", "30303", "Game", 20, new BigDecimal("9.99"),
                2, new BigDecimal("19.98"), new BigDecimal(".99"), new BigDecimal("4.99"), new BigDecimal("25.96"));
    }

    @Test
    public void findAddInvoice() {

        invoice = invoiceDao.addInvoice(invoice);
        Invoice invoice2 = invoiceDao.findInvoice(invoice.getInvoiceId());
        assertEquals(invoice, invoice2);


    }

    @Test
    public void findAllInvoices() {

        invoiceDao.addInvoice(invoice);
        List<Invoice> invoices = invoiceDao.findAllInvoices();
        assertEquals(1, invoices.size());
    }

    @Test
    public void updateInvoice() {
        invoice = invoiceDao.addInvoice(invoice);
        invoice.setQuantity(invoice.getQuantity()-1);
        invoiceDao.updateInvoice(invoice);
        Invoice invoice2 = invoiceDao.findInvoice(invoice.getInvoiceId());
        assertEquals(invoice, invoice2);
    }

    @Test
    public void deleteInvoice() {
        invoice = invoiceDao.addInvoice(invoice);
        invoiceDao.deleteInvoice(invoice.getInvoiceId());
        Invoice invoice2 = invoiceDao.findInvoice(invoice.getInvoiceId());
        assertNull(invoice2);

    }
}