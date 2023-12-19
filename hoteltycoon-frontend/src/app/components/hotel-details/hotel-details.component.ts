import {Component, Input} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute} from "@angular/router";
import {HotelDetails} from "../../../models/hotel-details.model";
import {HotelService} from "../../../services/hotel.service";
import {Facility} from "../../../models/facility.model";
import {BankService} from "../../../services/bank.service";
import {catchError, throwError} from "rxjs";
import {ErrorHandlerService} from "../../../services/error-handler.service";

@Component({
  selector: 'app-hotel-details',
  templateUrl: './hotel-details.component.html',
  styleUrls: ['./hotel-details.component.css']
})
export class HotelDetailsComponent {
  hotelId?: number;
  hotel?: HotelDetails;

  constructor(private route: ActivatedRoute, private http: HttpClient, private hotelService: HotelService, private moneyService: BankService, private errorHandlerService: ErrorHandlerService) {
    this.route.params.subscribe(params => {
      this.hotelId = params['hotelId'] as number;
    });
  }

  ngOnInit() {
    // Make HTTP requests to fetch data from your Spring Boot backend
    if (this.hotelId) {
      this.hotelService.getHotelDetails(this.hotelId).pipe(
        catchError((error: any) => {
          this.errorHandlerService.handleHttpError(error);
          return throwError(error);
        })
      ).subscribe(data => {
        this.hotel = data;
      });// 'hotelId' should match the parameter name in your route configuration
    }
  }

  buyFacility(facility: Facility) {
    if (this.hotelId) {
      this.hotelService.buyFacility(this.hotelId, facility.name).pipe(
        catchError((error: any) => {
          this.errorHandlerService.handleHttpError(error);
          return throwError(error);
        })
      ).subscribe(() => {
        this.ngOnInit();
        this.moneyService.newEvent("buy-facility");
      });
    }
  }

  buyBuilding() {
    if (this.hotelId) {
      this.hotelService.buyBuilding(this.hotelId).pipe(
        catchError((error: any) => {
          this.errorHandlerService.handleHttpError(error);
          return throwError(error);
        })
      ).subscribe(() => {
        this.ngOnInit();
        this.moneyService.newEvent("buy-building");
      });
    }
  }
}
