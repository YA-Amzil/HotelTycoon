export class Buildingplot {
  id: number;
  name: string;
  price: number;
  sold: boolean;

  constructor(id: number, name: string, price: number, sold: boolean) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.sold = sold;
  }
}
