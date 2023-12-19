import { Component, OnInit } from '@angular/core';
import { BankService } from '../../../services/bank.service';
import { faMoneyBills } from '@fortawesome/free-solid-svg-icons';
import {catchError, throwError} from "rxjs";
import {ErrorHandlerService} from "../../../services/error-handler.service";


@Component({
  selector: 'app-money',
  templateUrl: './money.component.html',
  styleUrls: ['./money.component.css']
})
export class MoneyComponent implements OnInit {
  moneyAmount?: number;
  faMoneyBills = faMoneyBills;

  constructor(private moneyService: BankService, private errorHandlerService: ErrorHandlerService) {
  }

  ngOnInit() {
    this.updateAmount();
    this.moneyService.events$.forEach(event => this.updateAmount());
  }

  updateAmount() {
    this.moneyService.fetchMoneyAmount().pipe(
      catchError((error: any) => {
        this.errorHandlerService.handleHttpError(error);
        return throwError(error);
      })
    ).subscribe((wallet: any) => {
      this.moneyAmount = wallet.amount;
    });
  }
}
