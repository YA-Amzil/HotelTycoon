import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Buildingplot} from "../models/buildingplot.model";
import {environment} from "../environment";

@Injectable({
  providedIn: 'root',
})
export class BuildingplotService {

  constructor(private http: HttpClient) {
  }

  buyBuildingPlot(buildingplotId: number): Observable<any> {
    // Make an HTTP POST request to your backend to buy the land
    return this.http.post(`${environment.backendUrl}/buildingplots/` +  buildingplotId + '/buy', []);
  }

  getBuildingplots(): Observable<Buildingplot[]> {
    return this.http.get<Buildingplot[]>(`${environment.backendUrl}/buildingplots`);
  }
}
