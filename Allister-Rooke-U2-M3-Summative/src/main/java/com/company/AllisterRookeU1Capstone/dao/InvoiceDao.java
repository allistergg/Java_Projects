package com.company.AllisterRookeU1Capstone.dao;

import com.company.AllisterRookeU1Capstone.model.Invoice;

import java.util.List;

public interface InvoiceDao {

    Invoice findInvoice(int id);

    List<Invoice> findAllInvoices();

    Invoice addInvoice(Invoice invoice);

    void updateInvoice(Invoice invoice);

    void deleteInvoice(int id);
}
