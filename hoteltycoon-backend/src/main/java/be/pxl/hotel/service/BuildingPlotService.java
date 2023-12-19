package be.pxl.hotel.service;

import java.util.List;

import be.pxl.hotel.api.request.CreateBuildingPlotRequest;
import be.pxl.hotel.api.response.BuildingPlotDTO;

public interface BuildingPlotService {

    List<BuildingPlotDTO> getAllBuildingPlots();

    Long createBuildingPlot(CreateBuildingPlotRequest createBuildingPlot);

    void buyBuildingPlot(Long buildingPlotId);

}
