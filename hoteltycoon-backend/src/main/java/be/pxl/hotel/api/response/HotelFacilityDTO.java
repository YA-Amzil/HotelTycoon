package be.pxl.hotel.api.response;

import be.pxl.hotel.domain.Facility;

public class HotelFacilityDTO {
	private Facility name;
	private double price;
	private boolean available;

	public Facility getName() {
		return name;
	}

	public void setName(Facility name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
}
