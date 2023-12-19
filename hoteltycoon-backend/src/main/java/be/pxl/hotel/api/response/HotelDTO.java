package be.pxl.hotel.api.response;

public class HotelDTO {
	private long id;
	private String name;
	private int stars;

	public HotelDTO(long id, String name, int stars) {
		this.id = id;
		this.name = name;
		this.stars = stars;
	}

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

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}
}
