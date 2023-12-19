package be.pxl.hotel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import be.pxl.hotel.api.request.CreateTransactionRequest;
import be.pxl.hotel.api.response.TransactionsOverviewDTO;
import be.pxl.hotel.api.response.WalletDTO;
import be.pxl.hotel.domain.Transaction;
import be.pxl.hotel.domain.TransactionType;
import be.pxl.hotel.domain.Wallet;
import be.pxl.hotel.exception.UnsufficientMoneyException;

@Service
public class BankService {
    private final Wallet wallet;

    public BankService(@Value("${initial.amount}") double initialAmount) {
        this.wallet = new Wallet(initialAmount);
    }

    public WalletDTO getWalletAmount() {
        return new WalletDTO(wallet.getAmount());
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void addTransaction(Transaction transaction) {
        if (transaction.getTransactionType() == TransactionType.PAYMENT) {
            wallet.registerPayment(transaction.getAmount(), transaction.getDescription());
        } else if (transaction.getTransactionType() == TransactionType.RECEIPT) {
            wallet.registerReceipt(transaction.getAmount(), transaction.getDescription());
        }
    }

    public CreateTransactionRequest createWalletTransaction(CreateTransactionRequest request)
            throws UnsufficientMoneyException, IllegalArgumentException {
        double amount = request.getAmount();
        String description = request.getDescription();
        TransactionType transactionType = request.getTransactionType();

        if (transactionType == TransactionType.PAYMENT) {
            wallet.registerPayment(amount, description);
        } else {
            wallet.registerReceipt(amount, description);
        }

        return request;
    }

    public TransactionsOverviewDTO getAllTransactionsOverview() {
        TransactionsOverviewDTO transactionsOverviewDTO = new TransactionsOverviewDTO();
        List<Transaction> transactions = wallet.getTransactions();

        List<Transaction> payments = transactions.stream()
                .filter(t -> t.getTransactionType() == TransactionType.PAYMENT)
                .toList();

        List<Transaction> receipts = transactions.stream()
                .filter(t -> t.getTransactionType() == TransactionType.RECEIPT)
                .toList();

        transactionsOverviewDTO.setPayments(payments);
        transactionsOverviewDTO.setReceipts(receipts);

        transactionsOverviewDTO.setTotalPayments(
                payments.stream().mapToDouble(Transaction::getAmount).sum());
        transactionsOverviewDTO.setTotalReceipts(
                receipts.stream().mapToDouble(Transaction::getAmount).sum());

        transactionsOverviewDTO.setAveragePayments(
                payments.isEmpty() ? 0 : transactionsOverviewDTO.getTotalPayments() / payments.size());
        transactionsOverviewDTO.setAverageReceipts(
                receipts.isEmpty() ? 0 : transactionsOverviewDTO.getTotalReceipts() / receipts.size());

        return transactionsOverviewDTO;
    }

}
