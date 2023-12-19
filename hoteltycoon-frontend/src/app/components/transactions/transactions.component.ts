import {Component, OnInit} from '@angular/core';
import {Transaction} from "../../../models/transaction.model";
import {BankService} from "../../../services/bank.service";
import {catchError, throwError} from "rxjs";
import {ErrorHandlerService} from "../../../services/error-handler.service";

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.css']
})
export class TransactionsComponent implements OnInit {

  receipts: Transaction[] = [];
  payments: Transaction[] = [];
  totalReceipts: number = 0;
  averageReceipts: number = 0;

  totalPayments: number = 0;
  averagePayments: number = 0;
  constructor(private bankService: BankService, private errorHandlerService: ErrorHandlerService) { }

  ngOnInit(): void {
    this.getTransactions();
  }

  getTransactions() {
    this.bankService.getTransactions().pipe(
      catchError((error: any) => {
        this.errorHandlerService.handleHttpError(error);
        return throwError(error);
      })
    ).subscribe((data) => {
      this.receipts = data.receipts;
      this.payments = data.payments;
      this.totalPayments = data.totalPayments;
      this.averagePayments = data.averagePayments;
      this.totalReceipts = data.totalReceipts;
      this.averageReceipts = data.averageReceipts;
    });
  }

}
