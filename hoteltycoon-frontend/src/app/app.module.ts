import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { BuildingplotDetailComponent } from './components/buildingplot-detail/buildingplot-detail.component';
import {HttpClientModule} from "@angular/common/http";
import { MoneyComponent } from './components/money/money.component';
import {FormsModule} from "@angular/forms";
import { NavbarComponent } from './components/navbar/navbar.component';
import {RouterModule} from "@angular/router";
import { AppRoutingModule } from './app-routing.module';
import { HotelsComponent } from './components/hotels/hotels.component';
import { TransactionsComponent } from './components/transactions/transactions.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import { HotelDetailsComponent } from './components/hotel-details/hotel-details.component';
import {CommonModule} from "@angular/common";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ToastrModule} from "ngx-toastr";

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    BuildingplotDetailComponent,
    MoneyComponent,
    NavbarComponent,
    HotelsComponent,
    TransactionsComponent,
    HotelDetailsComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule,
    AppRoutingModule,
    FontAwesomeModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
