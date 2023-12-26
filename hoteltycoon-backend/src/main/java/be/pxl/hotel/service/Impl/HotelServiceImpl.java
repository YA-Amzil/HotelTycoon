package be.pxl.hotel.service.Impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import be.pxl.hotel.api.request.CreateHotelRequest;
import be.pxl.hotel.api.response.HotelBuildingDTO;
import be.pxl.hotel.api.response.HotelDTO;
import be.pxl.hotel.api.response.HotelDetailDTO;
import be.pxl.hotel.api.response.HotelFacilityDTO;
import be.pxl.hotel.api.response.HotelPriceDTO;
import be.pxl.hotel.domain.BuildingPlot;
import be.pxl.hotel.domain.Facility;
import be.pxl.hotel.domain.Hotel;
import be.pxl.hotel.exception.ConstructionViolationException;
import be.pxl.hotel.exception.DuplicateFacilityException;
import be.pxl.hotel.exception.InvalidBookingException;
import be.pxl.hotel.exception.UnsufficientMoneyException;
import be.pxl.hotel.repository.BuildingPlotRepository;
import be.pxl.hotel.repository.HotelRepository;
import be.pxl.hotel.service.BankService;
import be.pxl.hotel.service.HotelService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HotelServiceImpl implements HotelService {
	private final HotelRepository hotelRepository;
	private final BuildingPlotRepository buildingPlotRepository;
	private final BankService bankService;

	public HotelServiceImpl(HotelRepository hotelRepository, BuildingPlotRepository buildingPlotRepository,
			BankService bankService) {
		this.hotelRepository = hotelRepository;
		this.buildingPlotRepository = buildingPlotRepository;
		this.bankService = bankService;
	}

	@Override
	@Transactional
	public List<Hotel> findAllOpenHotels() {
		return hotelRepository.findAll().stream()
				.filter(Hotel::isOpen)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional()
	public void book(Hotel hotel, int persons, int nights)
			throws InvalidBookingException, UnsufficientMoneyException {
		// TODO: book the given hotel for the given number of persons for the given
		// number of nights
		// make sure you receive the money on your wallet!
		hotel.book(persons, nights, bankService.getWallet());
	}

	@Override
	@Transactional()
	public List<HotelDTO> getAllHotels() {
		return hotelRepository.findAll()
				.stream()
				.map(hotel -> new HotelDTO(hotel.getId(), hotel.getName(), hotel.getStars()))
				.toList();
	}

	@Override
	@Transactional()
	public Long createHotel(CreateHotelRequest request)
			throws ConstructionViolationException, IllegalArgumentException {
		BuildingPlot buildingPlot = buildingPlotRepository.findById(request.buildingplotId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Building plot not found with id: " + request.buildingplotId() + "!"));

		if (hotelRepository.findByName(request.name()).isPresent()) {
			throw new ConstructionViolationException("Hotel with the same name already exists!");
		}

		if (hotelRepository.findByBuildingPlotId(request.buildingplotId()).isPresent()) {
			throw new ConstructionViolationException("There is already a hotel on the building plot!");
		}

		Hotel hotel = new Hotel(request.name());
		hotel.setBuildingPlot(buildingPlot);
		hotelRepository.save(hotel);
		return hotel.getId();
	}

	@Override
	@Transactional()
	public HotelDetailDTO getHotelDetails(Long hotelId) throws IllegalArgumentException {
		Hotel hotel = hotelRepository.findById(hotelId)
				.orElseThrow(() -> new IllegalArgumentException("Hotel not found with id: " + hotelId + "!"));

		HotelDetailDTO hotelDetailDTO = new HotelDetailDTO();
		hotelDetailDTO.setId(hotel.getId());
		hotelDetailDTO.setName(hotel.getName());
		hotelDetailDTO.setStarRating(hotel.getStars());
		hotelDetailDTO.setMaxBuildings(hotel.getBuildingPlot().getMaxBuildings());

		HotelPriceDTO priceDTO = new HotelPriceDTO();
		priceDTO.setPriceMainBuilding(hotel.getPriceMainBuilding());
		priceDTO.setPriceAdditionalBuilding(hotel.getPriceAdditionalBuilding());
		priceDTO.setPricePerNight(hotel.getPricePerNight());
		hotelDetailDTO.setPricelist(priceDTO);

		hotel.getBuildings().stream()
				.map(building -> {
					HotelBuildingDTO buildingDTO = new HotelBuildingDTO();
					buildingDTO.setName(building.getName());
					buildingDTO.setPrice(building.getPrice());
					return buildingDTO;
				})
				.forEach(hotelDetailDTO::addHotelBuildingDTO);

		List<HotelFacilityDTO> facilityDTOList = Arrays.stream(Facility.values())
				.map(facility -> {
					HotelFacilityDTO facilityDTO = new HotelFacilityDTO();
					facilityDTO.setName(facility);
					facilityDTO.setPrice(facility.getPrice());
					facilityDTO.setAvailable(hotel.hasFacility(facility));
					return facilityDTO;
				})
				.collect(Collectors.toList());

		hotelDetailDTO.setFacilities(facilityDTOList);

		return hotelDetailDTO;
	}

	@Override
	@Transactional()
	public void buyBuilding(Long hotelId)
			throws ConstructionViolationException, UnsufficientMoneyException, IllegalArgumentException {
		Hotel hotel = hotelRepository.findById(hotelId)
				.orElseThrow(() -> new IllegalArgumentException("Hotel not found with id: " + hotelId + "!"));

		hotel.addBuilding(bankService.getWallet());
	}

	@Override
	@Transactional()
	public void buyFacility(Long hotelId, Facility facility)
			throws ConstructionViolationException, UnsufficientMoneyException, IllegalArgumentException,
			DuplicateFacilityException {
		Hotel hotel = hotelRepository.findById(hotelId)
				.orElseThrow(() -> new IllegalArgumentException("Hotel not found with id: " + hotelId + "!"));

		hotel.addFacility(facility, bankService.getWallet());
	}
}
