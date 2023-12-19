package be.pxl.hotel.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import be.pxl.hotel.domain.Facility;
import be.pxl.hotel.domain.Hotel;
import jakarta.transaction.Transactional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByName(String name);

    Optional<Hotel> findByBuildingPlotId(long buildingplotId);

    @Modifying
    @Transactional
    @Query("UPDATE Hotel h SET h.facilities = :facilities WHERE h.id =:entityId")
    void updateFacilities(@Param("entityId") Long entityId, @Param("facilities") List<Facility> facilities);

}
