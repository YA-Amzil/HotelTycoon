package be.pxl.hotel.api.response;

public class BuildingPlotDTO {
	private long id;
	private double price;
	private int maxBuildings;
	private boolean sold;

	public BuildingPlotDTO(long id, double price, int maxBuildings, boolean sold) {
		this.id = id;
		this.price = price;
		this.maxBuildings = maxBuildings;
		this.sold = sold;
	}

	public long getId() {
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

}
