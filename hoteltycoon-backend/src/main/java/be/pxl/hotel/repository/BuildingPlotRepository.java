package be.pxl.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.pxl.hotel.domain.BuildingPlot;

@Repository
public interface BuildingPlotRepository extends JpaRepository<BuildingPlot, Long> {

}
