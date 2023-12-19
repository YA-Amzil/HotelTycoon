package be.pxl.hotel.api.response;

import java.util.ArrayList;
import java.util.List;

public class HotelDetailDTO {
	private long id;
	private String name;
	private int starRating;

	private int maxBuildings;

	private HotelPriceDTO pricelist;

	private List<HotelBuildingDTO> buildings = new ArrayList<>();
	private List<HotelFacilityDTO> facilities = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStarRating() {
		return starRating;
	}

	public void setStarRating(int starRating) {
		this.starRating = starRating;
	}

	public int getMaxBuildings() {
		return maxBuildings;
	}

	public void setMaxBuildings(int maxBuildings) {
		this.maxBuildings = maxBuildings;
	}

	public List<HotelBuildingDTO> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<HotelBuildingDTO> buildings) {
		this.buildings = buildings;
	}

	public List<HotelFacilityDTO> getFacilities() {
		return facilities;
	}

	public void setFacilities(List<HotelFacilityDTO> facilities) {
		this.facilities = facilities;
	}

	public void addHotelFacilityDTO(HotelFacilityDTO facilityDTO) {
		this.facilities.add(facilityDTO);
	}

	public void addHotelBuildingDTO(HotelBuildingDTO hotelBuildingDTO) {
		buildings.add(hotelBuildingDTO);
	}

	public void setPricelist(HotelPriceDTO pricelist) {
		this.pricelist = pricelist;
	}

	public HotelPriceDTO getPricelist() {
		return pricelist;
	}
}
