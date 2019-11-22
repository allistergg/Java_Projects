package com.company.AllisterRookeU1Capstone.dao;

import com.company.AllisterRookeU1Capstone.model.Invoice;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvoiceDaoJdbcTemplateImpl implements InvoiceDao {

    //    invoice_id int(11) not null auto_increment primary key,
//    name varchar(80) not null,
//    street varchar(30) not null,
//    city varchar(30) not null,
//    state varchar(30) not null,
//    zipcode varchar(5) not null,
//    item_type varchar(20) not null,
//    item_id int(11) not null,
//    unit_price decimal(5,2) not null,
//    quantity int(11) not null,
//    subtotal decimal(5,2) not null,
//    tax decimal(5,2) not null,
//    processing_fee decimal (5,2) not null,
//    total decimal(5,2) not null

    public final String FIND_INVOICE_SQL =
            "SELECT * FROM invoice WHERE invoice_id = ?";
    public final String FIND_ALL_INVOICES_SQL =
            "SELECT * FROM invoice";
    public final String ADD_INVOICE_SQL =
            "INSERT INTO invoice (name, street, city, state, zipcode, item_type, item_id, unit_price, quantity, subtotal, tax, processing_fee, total) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public final String UPDATE_INVOICE_SQL =
            "UPDATE invoice SET name =?, street = ?, city = ?, state = ?, zipcode = ?, item_type = ?, item_id = ?, unit_price = ?, quantity = ?, subtotal = ?, tax = ?, processing_fee = ?, total = ? WHERE invoice_id = ?";
    public final String DELETE_INVOICE_SQL =
            "DELETE FROM invoice WHERE invoice_id = ?";

    JdbcTemplate jdbcTemplate;

    public InvoiceDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Invoice findInvoice(int id) {
        try {
            return jdbcTemplate.queryForObject(FIND_INVOICE_SQL, new BeanPropertyRowMapper<>(Invoice.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Invoice> findAllInvoices() {
        return jdbcTemplate.query(FIND_ALL_INVOICES_SQL, new BeanPropertyRowMapper<>(Invoice.class));
    }

    @Override
    public Invoice addInvoice(Invoice invoice) {
        jdbcTemplate.update(ADD_INVOICE_SQL,
                invoice.getName(),
                invoice.getStreet(),
                invoice.getCity(),
                invoice.getState(),
                invoice.getZipcode(),
                invoice.getItemType(),
                invoice.getItemId(),
                invoice.getUnitPrice(),
                invoice.getQuantity(),
                invoice.getSubtotal(),
                invoice.getTax(),
                invoice.getProcessingFee(),
                invoice.getTotal());

        int id = jdbcTemplate.queryForObject("SELECT last_insert_id()", Integer.class);

        invoice.setInvoiceId(id);

        return invoice;
    }

    @Override
    public void updateInvoice(Invoice invoice) {

        jdbcTemplate.update(UPDATE_INVOICE_SQL,
                invoice.getName(),
                invoice.getStreet(),
                invoice.getCity(),
                invoice.getState(),
                invoice.getZipcode(),
                invoice.getItemType(),
                invoice.getItemId(),
                invoice.getUnitPrice(),
                invoice.getQuantity(),
                invoice.getSubtotal(),
                invoice.getTax(),
                invoice.getProcessingFee(),
                invoice.getTotal(),
                invoice.getInvoiceId());

    }

    @Override
    public void deleteInvoice(int id) {
        jdbcTemplate.update(DELETE_INVOICE_SQL, id);
    }
}
