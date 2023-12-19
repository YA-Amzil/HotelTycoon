package be.pxl.hotel.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Transaction {
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dateTime;
	private double amount;
	private TransactionType transactionType;
	private String description;

	public Transaction(double amount, TransactionType transactionType, String description) {
		this.dateTime = LocalDateTime.now();
		this.amount = amount;
		this.description = description;
		this.transactionType = transactionType;
	}

	public double getAmount() {
		return amount;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}
}
