package com.company.AllisterRookeU1Capstone.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

public class TShirt {

//    t_shirt_id int(11) not null auto_increment primary key,
//    size varchar(20) not null,
//    color varchar(20) not null,
//    description varchar(255) not null,
//    price decimal(5,2) not null,
//    quantity int(11) not null



    @Id
    private int tShirtId;
    @NotNull
    @Length(max=20)
    private String size;
    @NotNull
    @Length(max=20)
    private String color;
    @NotNull
    @Length(max=255)
    private String description;
    @NotNull
    @Positive
    private BigDecimal price;
    @NotNull
    @Positive
    private Integer quantity;

    public TShirt() {
    }

    public TShirt(int tShirtId, String size, String color, String description, BigDecimal price, int quantity) {
        this.tShirtId = tShirtId;
        this.size = size;
        this.color = color;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public TShirt(String size, String color, String description, BigDecimal price, int quantity) {
        this.size = size;
        this.color = color;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public int getTShirtId() {
        return tShirtId;
    }

    public void setTShirtId(int tShirtId) {
        this.tShirtId = tShirtId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TShirt tShirt = (TShirt) o;
        return getTShirtId() == tShirt.getTShirtId() &&
                getQuantity() == tShirt.getQuantity() &&
                getSize().equals(tShirt.getSize()) &&
                getColor().equals(tShirt.getColor()) &&
                getDescription().equals(tShirt.getDescription()) &&
                getPrice().equals(tShirt.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTShirtId(), getSize(), getColor(), getDescription(), getPrice(), getQuantity());
    }
}
