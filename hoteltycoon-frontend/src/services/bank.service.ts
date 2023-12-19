import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable, map, Subject} from "rxjs";
import {TransactionOverview} from "../models/transaction-overview.model";
import {environment} from "../environment";

@Injectable({
  providedIn: 'root'
})
export class BankService {
  private moneyAmount: number = 0.0;

  constructor(private http: HttpClient) {}

  private _subject = new Subject<any>();

  newEvent(event: any) {
    this._subject.next(event);
  }

  get events$ () {
    return this._subject.asObservable();
  }

  fetchMoneyAmount(): Observable<number>  {
    return this.http.get(`${environment.backendUrl}/wallet`).pipe(
      map((data: any) => {
        this.moneyAmount = data;
        return this.moneyAmount;
      })
    );
  }

  getMoneyAmount(): number {
    return this.moneyAmount;
  }

  getTransactions(): Observable<TransactionOverview> {
    return this.http.get<TransactionOverview>(`${environment.backendUrl}/wallet/transactions`);
  }
}
