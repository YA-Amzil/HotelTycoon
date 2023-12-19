package be.pxl.hotel.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Entity
@Table(name = "buildings")
public class Building {
    private static final Logger LOGGER = LogManager.getLogger(Hotel.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime constructionDate;
    private double price;
    private String name;
    @ManyToOne
    @JoinColumn(name = "hotels")
    private Hotel hotel;

    public Building() {
        // JPA Only
    }

    public Building(LocalDateTime constructionDate, double price, String name, Hotel hotel) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Creating building with construction date {}, price {} and name {}", constructionDate, price,
                    name);
        }

        this.constructionDate = LocalDateTime.now();
        this.price = price;
        this.name = name;
        this.hotel = hotel;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getConstructionDate() {
        return constructionDate;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hotel getHotel() {
        return hotel;
    }

    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", constructionDate=" + constructionDate +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", hotel=" + hotel +
                '}';
    }
}