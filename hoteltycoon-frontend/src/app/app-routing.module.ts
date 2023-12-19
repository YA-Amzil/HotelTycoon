import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {provideRouter, RouterModule, Routes, withComponentInputBinding} from "@angular/router";
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {HotelsComponent} from "./components/hotels/hotels.component";
import {TransactionsComponent} from "./components/transactions/transactions.component";
import {HotelDetailsComponent} from "./components/hotel-details/hotel-details.component";

const routes: Routes = [
  { path: 'buildingplots', component: DashboardComponent },
  { path: 'hotels', component: HotelsComponent },
  { path: 'transactions', component: TransactionsComponent },
  { path: 'hotel-details/:hotelId', component: HotelDetailsComponent }
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule],
})
export class AppRoutingModule { }
