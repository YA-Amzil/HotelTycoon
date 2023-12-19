import {Transaction} from "./transaction.model";

export interface TransactionOverview {
  receipts: Transaction[];
  payments: Transaction[];

  totalPayments: number;
  averagePayments: number;
  totalReceipts: number;
  averageReceipts: number;
}
