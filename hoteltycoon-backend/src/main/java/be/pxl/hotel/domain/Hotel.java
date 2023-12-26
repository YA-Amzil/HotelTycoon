package be.pxl.hotel.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import be.pxl.hotel.exception.ConstructionViolationException;
import be.pxl.hotel.exception.DuplicateFacilityException;
import be.pxl.hotel.exception.InvalidBookingException;
import be.pxl.hotel.exception.UnsufficientMoneyException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Entity
@Table(name = "hotels")
public class Hotel {
    private static final Logger LOGGER = LogManager.getLogger(Hotel.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "buildingplots")
    private BuildingPlot buildingPlot;

    private String name;
    @Enumerated(value = EnumType.STRING)
    private List<Facility> facilities = new ArrayList<>();

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Building> buildings = new ArrayList<>();

    public Hotel() {
        // JPA Only
    }

    public Hotel(String name) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Creating hotel with building plot {} and name {}", name);
        }
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public BuildingPlot getBuildingPlot() {
        return buildingPlot;
    }

    public void setBuildingPlot(BuildingPlot buildingPlot) {
        this.buildingPlot = buildingPlot;
    }

    public String getName() {
        return name;
    }

    public List<Facility> getFacilities() {
        return facilities;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public double getPriceMainBuilding() {
        return 1.5 * buildingPlot.getPrice();
    }

    public double getPriceAdditionalBuilding() {
        return 1.1 * buildingPlot.getPrice();
    }

    public int getNumberOfBuildings() {
        return buildings.size();
    }

    public void addBuilding(Wallet wallet) {

        if (buildingPlot.getMaxBuildings() == buildings.size()) {
            throw new ConstructionViolationException("You can't add a building to this hotel!");
        }

        if (wallet.getAmount() < buildings.size() * buildingPlot.getPrice()) {
            throw new UnsufficientMoneyException("Insufficient funds to buy a building!");
        }

        if (buildings.size() == 0) {
            wallet.registerPayment(getPriceMainBuilding(), "Payment for main building");
            Building building = new Building(LocalDateTime.now(), getPriceMainBuilding(), "Main Building", this);
            buildings.add(building);
        } else {
            wallet.registerPayment(getPriceAdditionalBuilding(), "Payment for additional building");
            Building building = new Building(LocalDateTime.now(), getPriceAdditionalBuilding(),
                    "Additional Building " + buildings.size(), this);
            buildings.add(building);
        }

    }

    public boolean isOpen() {
        return !buildings.isEmpty();
    }

    public void addFacility(Facility facility, Wallet wallet) {
        if (buildings.size() == 0) {
            throw new ConstructionViolationException("Cannot add facility before main building is constructed!");
        }

        if (hasFacility(facility)) {
            throw new DuplicateFacilityException("Facility already exists in the hotel!");
        }

        if (wallet.getAmount() < facility.getPrice()) {
            throw new UnsufficientMoneyException("Insufficient funds to add facility!");
        }

        wallet.registerPayment(facility.getPrice(), "Payment for facility " + facility.name());
        facilities.add(facility);
    }

    public boolean hasFacility(Facility facility) {
        return facilities.contains(facility);
    }

    public int getStars() {
        int stars = buildings.size() + facilities.size();
        return Math.min(stars, 5);
    }

    public double getPricePerNight() {
        double bassePrice = buildingPlot.getPrice() / 400.0;
        double starSurcharge = (getStars() > 1) ? (getStars() - 1) * bassePrice / 3 : 0;
        return bassePrice + starSurcharge + getPriceFacilities();
    }

    public void book(int numberOfPersons, int numberOfNights, Wallet wallet) {
        if (!isOpen()) {
            throw new InvalidBookingException("Cannot book a room in a hotel without buildings!");
        }

        double totalPrice = getPricePerNight() * numberOfPersons * numberOfNights;
        if (wallet.getAmount() < totalPrice) {
            throw new UnsufficientMoneyException("Insufficient funds to book a room!");
        }

        wallet.registerReceipt(totalPrice, "Payment for booking a room for " + numberOfPersons + " persons for "
                + numberOfNights + " nights");
    }

    public double getPriceFacilities() {
        return facilities.stream().findFirst().map(Facility::getPrice).orElse(0.0);
    }

    public double getTotalCost() {
        return buildingPlot.getPrice() + getPriceFacilities();
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", buildingPlot=" + buildingPlot +
                ", name='" + name + '\'' +
                ", facilities=" + facilities +
                ", buildings=" + buildings +
                '}';
    }
}
