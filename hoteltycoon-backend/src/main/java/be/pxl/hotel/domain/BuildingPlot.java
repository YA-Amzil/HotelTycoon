package be.pxl.hotel.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "buildingplots")
public class BuildingPlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double price;
    private int maxBuildings;
    private boolean sold;

    public BuildingPlot() {
        // JPA Only
    }

    public BuildingPlot(double price, int maxBuildings, boolean sold) {
        this.price = price;
        this.maxBuildings = maxBuildings;
        this.sold = sold;
    }

    public Long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getMaxBuildings() {
        return maxBuildings;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setMaxBuildings(int maxBuildings) {
        this.maxBuildings = maxBuildings;
    }

    @Override
    public String toString() {
        return "BuildingPlot{" +
                "id=" + id +
                ", price=" + price +
                ", maxBuildings=" + maxBuildings +
                ", sold=" + sold +
                '}';
    }

}
