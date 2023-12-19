package be.pxl.hotel.api.response;

public class HotelPriceDTO {
	private double priceMainBuilding;
	private double priceAdditionalBuilding;
	private double pricePerNight;

	public double getPriceMainBuilding() {
		return priceMainBuilding;
	}

	public void setPriceMainBuilding(double priceMainBuilding) {
		this.priceMainBuilding = priceMainBuilding;
	}

	public double getPriceAdditionalBuilding() {
		return priceAdditionalBuilding;
	}

	public void setPriceAdditionalBuilding(double priceAdditionalBuilding) {
		this.priceAdditionalBuilding = priceAdditionalBuilding;
	}

	public double getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(double pricePerNight) {
		this.pricePerNight = pricePerNight;
	}
}
