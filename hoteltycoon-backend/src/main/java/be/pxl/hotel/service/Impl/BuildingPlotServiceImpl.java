package be.pxl.hotel.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import be.pxl.hotel.api.request.CreateBuildingPlotRequest;
import be.pxl.hotel.api.response.BuildingPlotDTO;
import be.pxl.hotel.domain.BuildingPlot;
import be.pxl.hotel.domain.Transaction;
import be.pxl.hotel.domain.TransactionType;
import be.pxl.hotel.exception.ConstructionViolationException;
import be.pxl.hotel.exception.UnsufficientMoneyException;
import be.pxl.hotel.repository.BuildingPlotRepository;
import be.pxl.hotel.service.BankService;
import be.pxl.hotel.service.BuildingPlotService;

@Service
public class BuildingPlotServiceImpl implements BuildingPlotService {
    private final BuildingPlotRepository buildingPlotRepository;
    private final BankService bankService;

    private void initializeBuildingPlots() {
        createBuildingPlot(new CreateBuildingPlotRequest(75000, 4, false));
        createBuildingPlot(new CreateBuildingPlotRequest(100000, 3, false));
        createBuildingPlot(new CreateBuildingPlotRequest(105000, 1, false));
        createBuildingPlot(new CreateBuildingPlotRequest(135000, 4, false));
    }

    public BuildingPlotServiceImpl(BuildingPlotRepository buildingPlotRepository, BankService bankService) {
        this.buildingPlotRepository = buildingPlotRepository;
        this.bankService = bankService;
        initializeBuildingPlots();
    }

    @Override
    public List<BuildingPlotDTO> getAllBuildingPlots() {
        return buildingPlotRepository
                .findAll()
                .stream()
                .map(buildingPlot -> new BuildingPlotDTO(
                        buildingPlot.getId(),
                        buildingPlot.getPrice(),
                        buildingPlot.getMaxBuildings(),
                        buildingPlot.isSold()))
                .toList();
    }

    @Override
    public Long createBuildingPlot(CreateBuildingPlotRequest createBuildingPlot) {
        BuildingPlot buildingPlot = new BuildingPlot();
        buildingPlot.setPrice(createBuildingPlot.getPrice());
        buildingPlot.setMaxBuildings(createBuildingPlot.getMaxBuildings());
        buildingPlot.setSold(createBuildingPlot.isSold());
        BuildingPlot newBuildingplot = buildingPlotRepository.save(buildingPlot);
        return newBuildingplot.getId();
    }

    @Override
    public void buyBuildingPlot(Long buildingPlotId)
            throws UnsufficientMoneyException, IllegalArgumentException {
        BuildingPlot buildingPlot = buildingPlotRepository.findById(buildingPlotId)
                .orElseThrow(() -> new IllegalArgumentException("Building plot not found!"));

        if (buildingPlot.getPrice() > bankService.getWallet().getAmount()) {
            throw new UnsufficientMoneyException("Insufficient funds to buy building plot!");
        }

        if (buildingPlot.isSold()) {
            throw new ConstructionViolationException("Building plot is already sold!");
        }

        Transaction transaction = new Transaction(buildingPlot.getPrice(), TransactionType.PAYMENT,
                "Building plot " + buildingPlotId + " bought");
        bankService.addTransaction(transaction);
        buildingPlot.setSold(true);
        buildingPlotRepository.save(buildingPlot);
    }
}
