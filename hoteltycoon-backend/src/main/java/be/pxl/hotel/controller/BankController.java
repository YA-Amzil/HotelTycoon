package be.pxl.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.pxl.hotel.api.request.CreateTransactionRequest;
import be.pxl.hotel.api.response.TransactionsOverviewDTO;
import be.pxl.hotel.api.response.WalletDTO;
import be.pxl.hotel.exception.UnsufficientMoneyException;
import be.pxl.hotel.service.BankService;

@RestController
@RequestMapping("/wallet")
public class BankController {
    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping
    public ResponseEntity<WalletDTO> getWalletAmount() {
        return ResponseEntity.ok(bankService.getWalletAmount());
    }

    @PostMapping("/transactions")
    public ResponseEntity<CreateTransactionRequest> addWallet(@RequestBody CreateTransactionRequest request) {
        try {
            var bank = bankService.createWalletTransaction(request);
            return new ResponseEntity<CreateTransactionRequest>(bank, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<CreateTransactionRequest>(HttpStatus.BAD_REQUEST);
        } catch (UnsufficientMoneyException e) {
            return new ResponseEntity<CreateTransactionRequest>(HttpStatus.PAYMENT_REQUIRED);
        }
    }

    @GetMapping("/transactions")
    public ResponseEntity<TransactionsOverviewDTO> getAllTransactions() {
        return ResponseEntity.ok(bankService.getAllTransactionsOverview());
    }
}