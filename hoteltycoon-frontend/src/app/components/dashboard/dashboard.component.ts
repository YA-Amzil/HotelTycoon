import { Component, OnInit } from '@angular/core';
import { BuildingplotService } from '../../../services/buildingplot.service';
import { Buildingplot } from '../../../models/buildingplot.model';
import {ErrorHandlerService} from "../../../services/error-handler.service";
import {catchError, throwError} from "rxjs";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  buildingplots: Buildingplot[] = [];

  constructor(private landService: BuildingplotService, private errorHandlerService: ErrorHandlerService) {}

  ngOnInit() {
    this.landService.getBuildingplots().pipe(
      catchError((error: any) => {
        this.errorHandlerService.handleHttpError(error);
        return throwError(error);
      })
    ).subscribe((buildingplots) => {
      this.buildingplots = buildingplots;
    });
  }

  handleChildEvent($event: any) {
    this.ngOnInit();
  }
}
