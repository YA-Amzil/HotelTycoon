package be.pxl.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.pxl.hotel.api.request.CreateHotelRequest;
import be.pxl.hotel.api.response.HotelDTO;
import be.pxl.hotel.api.response.HotelDetailDTO;
import be.pxl.hotel.domain.Facility;
import be.pxl.hotel.service.HotelService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public ResponseEntity<List<HotelDTO>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelDetailDTO> getHotelDetails(@PathVariable Long hotelId) {
        return new ResponseEntity<HotelDetailDTO>(hotelService.getHotelDetails(hotelId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> createHotel(@Valid @RequestBody CreateHotelRequest request) {
        hotelService.createHotel(request);
        return new ResponseEntity<Long>(HttpStatus.CREATED);
    }

    @PostMapping("/{hotelId}/buy-building")
    public ResponseEntity<Void> buyBuilding(@PathVariable Long hotelId) {
        hotelService.buyBuilding(hotelId);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PostMapping("/{hotelId}/buy-facility/{facility}")
    public ResponseEntity<Void> buyFacility(@PathVariable Long hotelId, @PathVariable Facility facility) {
        hotelService.buyFacility(hotelId, facility);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

}
