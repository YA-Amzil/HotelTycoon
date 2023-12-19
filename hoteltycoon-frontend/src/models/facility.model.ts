export class Facility {
  name: string;
  price: number;
  available: boolean;


  constructor(name: string, price: number, available: boolean) {
    this.name = name;
    this.price = price;
    this.available = available;
  }
}

