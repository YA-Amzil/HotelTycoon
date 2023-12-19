import {Facility} from "./facility.model";
import {Building} from "./building.model";
import {Pricelist} from "./pricelist.model";

export class HotelDetails {
  name: string;
  starRating: number;
  maxBuildings: number;

  pricelist: Pricelist;

  buildings: Building[] = [];
  facilities: Facility[] = [];

  constructor(name: string, starRating: number, maxBuildings: number, facilities: Facility[], buildings: Building[], pricelist: Pricelist) {
    this.name = name;
    this.starRating = starRating;
    this.maxBuildings = maxBuildings;
    this.facilities = facilities;
    this.buildings = buildings;
    this.pricelist = pricelist;
  }
}
