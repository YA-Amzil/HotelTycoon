import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Buildingplot} from "../models/buildingplot.model";
import {Hotel} from "../models/hotel.model";
import {HotelDetails} from "../models/hotel-details.model";
import {Facility} from "../models/facility.model";
import {environment} from "../environment";

@Injectable({
  providedIn: 'root',
})
export class HotelService {// Replace with your actual API endpoint

  constructor(private http: HttpClient) {
  }

  createHotel(hotelName: string, buildingplotId: number): Observable<any> {
    const headers = { 'Content-Type': 'application/json' };
    // Make an HTTP POST request to your backend to buy the land
    const body = {
      "name": hotelName,
      "buildingplotId": buildingplotId
    };
    return this.http.post(`${environment.backendUrl}/hotels`, body, { headers });
  }

  getHotels(): Observable<Hotel[]> {
    return this.http.get<Hotel[]>(`${environment.backendUrl}/hotels`);
  }

  getHotelDetails(hotelId: number): Observable<HotelDetails> {
    return this.http.get<HotelDetails>(`${environment.backendUrl}/hotels/` + hotelId);
  }

  buyBuilding(hotelId: number) {
    return this.http.post(`${environment.backendUrl}/hotels/` + hotelId + `/buy-building`, null);
  }

  buyFacility(hotelId: number, facility: string) {
    return this.http.post(`${environment.backendUrl}/hotels/` + hotelId + `/buy-facility/` + facility, null);
  }
}
