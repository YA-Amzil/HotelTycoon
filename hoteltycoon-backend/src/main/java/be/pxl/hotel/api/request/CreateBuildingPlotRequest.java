package be.pxl.hotel.api.request;

public class CreateBuildingPlotRequest {
    private double price;
    private int maxBuildings;
    private boolean sold;

    public CreateBuildingPlotRequest(double price, int maxBuildings, boolean sold) {
        this.price = price;
        this.maxBuildings = maxBuildings;
        this.sold = sold;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setMaxBuildings(int maxBuildings) {
        this.maxBuildings = maxBuildings;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
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
}
