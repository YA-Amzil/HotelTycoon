package be.pxl.hotel.service;

import java.util.List;

import be.pxl.hotel.api.request.CreateHotelRequest;
import be.pxl.hotel.api.response.HotelDTO;
import be.pxl.hotel.api.response.HotelDetailDTO;
import be.pxl.hotel.domain.Facility;
import be.pxl.hotel.domain.Hotel;

public interface HotelService {

	Long createHotel(CreateHotelRequest request);

	List<HotelDTO> getAllHotels();

	HotelDetailDTO getHotelDetails(Long hotelId);

	void buyBuilding(Long hotelId);

	void buyFacility(Long hotelId, Facility facility);

	List<Hotel> findAllOpenHotels();

	void book(Hotel hotel, int persons, int nights);
}