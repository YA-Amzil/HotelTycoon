package be.pxl.hotel.domain;

public enum Facility {

    SWIMMING_POOL(17500.0),
    FITNESS(15500.0),
    RESTAURANT(60000.0),
    WELLNESS(45000.0);

    private double price;

    Facility(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
