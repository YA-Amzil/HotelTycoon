package be.pxl.hotel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.pxl.hotel.domain.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByName(String name);

    Optional<Hotel> findByBuildingPlotId(long buildingplotId);

}
