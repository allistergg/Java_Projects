package com.company.AllisterRookeU1Capstone.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

public class Game {

//    game_id int(11) not null auto_increment primary key,
//    title varchar(50) not null,
//    esrb_rating varchar(50) not null,
//    description varchar(255) not null,
//    price decimal(5, 2) not null,
//    studio varchar(50) not null,
//    quantity int(11)

    @Id
    private int gameId;
    @NotNull
    @Length(max=50)
    private String title;
    @NotNull
    @Length(max=50)
    private String esrbRating;
    @NotNull
    @Length(max=255)
    private String description;
    @NotNull
    @Positive
    private BigDecimal price;
    @NotNull
    @Length(max=50)
    private String studio;
    @NotNull
    @Positive
    private Integer quantity;

    public Game() {
    }

    public Game(int gameId, String title, String esrbRating, String description, BigDecimal price, String studio, int quantity) {
        this.gameId = gameId;
        this.title = title;
        this.esrbRating = esrbRating;
        this.description = description;
        this.price = price;
        this.studio = studio;
        this.quantity = quantity;
    }

    public Game(String title, String esrbRating, String description, BigDecimal price, String studio, int quantity) {
        this.title = title;
        this.esrbRating = esrbRating;
        this.description = description;
        this.price = price;
        this.studio = studio;
        this.quantity = quantity;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEsrbRating() {
        return esrbRating;
    }

    public void setEsrbRating(String esrbRating) {
        this.esrbRating = esrbRating;
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

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
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
        Game game = (Game) o;
        return getGameId() == game.getGameId() &&
                getQuantity() == game.getQuantity() &&
                getTitle().equals(game.getTitle()) &&
                getEsrbRating().equals(game.getEsrbRating()) &&
                getDescription().equals(game.getDescription()) &&
                getPrice().equals(game.getPrice()) &&
                getStudio().equals(game.getStudio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGameId(), getTitle(), getEsrbRating(), getDescription(), getPrice(), getStudio(), getQuantity());
    }
}
