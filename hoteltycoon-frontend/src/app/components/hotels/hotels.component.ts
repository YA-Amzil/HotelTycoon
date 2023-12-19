import {Component, OnInit} from '@angular/core';
import {Hotel} from "../../../models/hotel.model";
import {HotelService} from "../../../services/hotel.service";
import {Router} from "@angular/router";
import {catchError, throwError} from "rxjs";
import {ErrorHandlerService} from "../../../services/error-handler.service";

@Component({
  selector: 'app-hotels',
  templateUrl: './hotels.component.html',
  styleUrls: ['./hotels.component.css']
})
export class HotelsComponent implements OnInit {
  hotels: Hotel[] = [];
  constructor(private hotelService: HotelService, private router: Router, private errorHandlerService: ErrorHandlerService) { }

  ngOnInit() {
    this.hotelService.getHotels().pipe(
      catchError((error: any) => {
        this.errorHandlerService.handleHttpError(error);
        return throwError(error);
      })
    ).subscribe((hotels) => {
      this.hotels = hotels;
    });
  }

  navigateToHotelDetail(hotelId: number) {
    this.router.navigate(['/hotel-details', hotelId]);
  }

}
