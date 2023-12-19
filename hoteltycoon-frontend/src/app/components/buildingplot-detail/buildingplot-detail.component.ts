import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BuildingplotService} from '../../../services/buildingplot.service';
import {Buildingplot} from "../../../models/buildingplot.model";
import {HotelService} from "../../../services/hotel.service";
import {BankService} from "../../../services/bank.service";
import {ErrorHandlerService} from "../../../services/error-handler.service";
import {catchError, throwError} from "rxjs";

@Component({
  selector: 'app-buildingplot-detail',
  templateUrl: './buildingplot-detail.component.html',
  styleUrls: ['./buildingplot-detail.component.css'],
})
export class BuildingplotDetailComponent {
  @Input() buildingplot!: Buildingplot; // Input property to receive land data
  @Output() childEvent = new EventEmitter<any>();
  showHotelDialog = false;
  hotelName: string = '';

  constructor(private landService: BuildingplotService,
              private hotelService: HotelService,
              private moneyService: BankService,
              private errorHandlerService: ErrorHandlerService) {
  }

  openHotelDialog() {
    this.showHotelDialog = true;
  }

  createHotel() {
    this.hotelService.createHotel(this.hotelName, this.buildingplot.id).pipe(
      catchError((error: any) => {
        this.errorHandlerService.handleHttpError(error);
        return throwError(error);
      })
    ).subscribe(() => {
      this.childEvent.emit();
    });
    // Reset the dialog and clear the hotel name input

  }

  buyLand() {
    // Assuming you have a method in your services to make the API call
    this.landService.buyBuildingPlot(this.buildingplot.id).pipe(
      catchError((error: any) => {
        this.errorHandlerService.handleHttpError(error);
        return throwError(error);
      })
    ).subscribe(
      (response) => {
        // Handle the response from the backend after a successful purchase
        console.log('Purchase successful!', response);
        this.moneyService.newEvent("buy-buildingplot");
        this.childEvent.emit();
      }
    );
  }
}
