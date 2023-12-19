package be.pxl.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.pxl.hotel.api.response.BuildingPlotDTO;
import be.pxl.hotel.service.BuildingPlotService;

@RestController
@RequestMapping("/buildingplots")
public class BuildingPlotController {
    private final BuildingPlotService buildingPlotService;

    @Autowired
    public BuildingPlotController(BuildingPlotService buildingPlotService) {
        this.buildingPlotService = buildingPlotService;
    }

    @GetMapping
    public ResponseEntity<List<BuildingPlotDTO>> getAllBuildingPlots() {
        return ResponseEntity.ok(buildingPlotService.getAllBuildingPlots());
    }

    @PostMapping("/{buildingPlotId}/buy")
    public ResponseEntity<Void> buyBuildingPlot(@PathVariable Long buildingPlotId) {
        buildingPlotService.buyBuildingPlot(buildingPlotId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
